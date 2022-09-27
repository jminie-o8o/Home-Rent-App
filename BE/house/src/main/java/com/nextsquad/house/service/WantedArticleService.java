package com.nextsquad.house.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.dto.SearchConditionDto;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.dto.wantedArticle.*;
import com.nextsquad.house.exception.*;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.repository.UserRepository;
import com.nextsquad.house.repository.WantedArticleBookmarkRepository;
import com.nextsquad.house.repository.WantedArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WantedArticleService {

    private final WantedArticleRepository wantedArticleRepository;
    private final UserRepository userRepository;
    private final WantedArticleBookmarkRepository wantedArticleBookmarkRepository;
    private final JwtProvider jwtProvider;

    public SavedWantedArticleResponse writeWantedArticle(WantedArticleRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException());
        WantedArticle wantedArticle = WantedArticle.builder()
                .user(user)
                .address(request.getAddress())
                .title(request.getTitle())
                .content(request.getContent())
                .moveInDate(request.getMoveInDate())
                .moveOutDate(request.getMoveOutDate())
                .rentBudget(request.getRentBudget())
                .depositBudget(request.getDepositBudget())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        wantedArticleRepository.save(wantedArticle);
        return new SavedWantedArticleResponse(wantedArticle.getId());
    }


    public WantedArticleResponse getWantedArticle(Long articleId, String token) {
        DecodedJWT decode = jwtProvider.decode(token);
        Long id = decode.getClaim("id").asLong();
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        WantedArticle article = wantedArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException());
        if (article.isDeleted()) {
            throw new IllegalArgumentException("삭제된 글 입니다.");
        }

        boolean isBookmarked = wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, article).isPresent();

        article.addViewCount();
        return new WantedArticleResponse(article, isBookmarked);
    }
    
    public WantedArticleListResponse getWantedArticleList(SearchConditionDto searchCondition, Pageable pageable, String token) {

        DecodedJWT decode = jwtProvider.decode(token);
        Long id = decode.getClaim("id").asLong();
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        List<WantedArticleBookmark> listByUser = wantedArticleBookmarkRepository.findListByUser(user);
        Map<Long, Boolean> bookmarkHashMap = new HashMap<Long, Boolean>();
        for (WantedArticleBookmark wantedArticleBookmark : listByUser) {
            bookmarkHashMap.put(wantedArticleBookmark.getWantedArticle().getId(), true);
        }

        List<WantedArticle> wantedArticles = wantedArticleRepository.findByKeyword(searchCondition, pageable);
        boolean hasNext = hasNext(pageable, wantedArticles);
        if (hasNext) {
            wantedArticles = wantedArticles.subList(0, wantedArticles.size()-1);
        }
        List<WantedArticleElementResponse> elementResponseList = wantedArticles.stream()
                .map(WantedArticleElementResponse::from)
                .peek(element -> {element.setBookmarked(bookmarkHashMap.get(element.getId()) != null);})
                .collect(Collectors.toList());
        return new WantedArticleListResponse(elementResponseList, hasNext);
    }

    private boolean hasNext(Pageable pageable, List<?> rentArticles) {
        return pageable.getPageSize() < rentArticles.size();
    }


    public GeneralResponseDto deleteWantedArticle(Long id, String accessToken) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException());

        authorizeArticleOwner(accessToken, wantedArticle);

        wantedArticle.markAsDeleted();
        wantedArticleBookmarkRepository.deleteByWantedArticle(wantedArticle);
        return new GeneralResponseDto(200, "게시글이 삭제되었습니다.");
    }
    
    public GeneralResponseDto updateWantedArticle(Long id, WantedArticleRequest request, String accessToken) {
        WantedArticle article = wantedArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException());

        authorizeArticleOwner(accessToken, article);

        article.modifyArticle(request);
        return new GeneralResponseDto(200, "게시글이 수정되었습니다.");
    }

    public GeneralResponseDto addWantedBookmark(BookmarkRequestDto bookmarkRequestDto, String token) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException());

        Long loginedId = jwtProvider.decode(token).getClaim("id").asLong();

        User user = userRepository.findById(loginedId)
                .orElseThrow(() -> new UserNotFoundException());

        if (wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, wantedArticle).isPresent()) {
            throw new DuplicateBookmarkException();
        }

        if (wantedArticle.isDeleted()) {
            throw new IllegalArgumentException("삭제된 게시글은 추가할 수 없습니다.");
        }
        if (wantedArticle.isCompleted()) {
            throw new IllegalArgumentException("삭제된 게시글은 추가할 수 없습니다.");
        }

        wantedArticleBookmarkRepository.save(new WantedArticleBookmark(user, wantedArticle));
        return new GeneralResponseDto(200, "북마크에 추가 되었습니다.");
    }

    public GeneralResponseDto deleteWantedBookmark(BookmarkRequestDto bookmarkRequestDto, String token) {
        Long loginedId = jwtProvider.decode(token).getClaim("id").asLong();

        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException());
        User user = userRepository.findById(loginedId)
                .orElseThrow(() -> new UserNotFoundException());
        WantedArticleBookmark bookmark = wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, wantedArticle)
                .orElseThrow(() -> new BookmarkNotFoundException());

        wantedArticleBookmarkRepository.delete(bookmark);
        return new GeneralResponseDto(200, "북마크가 삭제되었습니다.");
    }

    private void authorizeArticleOwner(String accessToken, WantedArticle article) {
        Long loggedInId = jwtProvider.decode(accessToken).getClaim("id").asLong();
        User user = userRepository.findById(loggedInId)
                .orElseThrow(UserNotFoundException::new);

        if (!user.equals(article.getUser())) {
            throw new AccessDeniedException();
        }
    }
}
