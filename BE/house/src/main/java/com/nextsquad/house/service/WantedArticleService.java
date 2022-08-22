package com.nextsquad.house.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.dto.SearchConditionDto;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.dto.wantedArticle.*;
import com.nextsquad.house.exception.ArticleNotFoundException;
import com.nextsquad.house.exception.BookmarkNotFoundException;
import com.nextsquad.house.exception.UserNotFoundException;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.repository.UserRepository;
import com.nextsquad.house.repository.WantedArticleBookmarkRepository;
import com.nextsquad.house.repository.WantedArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    public WantedArticleResponse getWantedArticle(Long articleId) {
        WantedArticle article = wantedArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException());
        article.addViewCount();
        return WantedArticleResponse.from(article);
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


    public GeneralResponseDto deleteWantedArticle(Long id) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException());
        wantedArticle.markAsDeleted();
        wantedArticleRepository.save(wantedArticle);
        return new GeneralResponseDto(200, "게시글이 삭제되었습니다.");
    }
    
    public GeneralResponseDto updateWantedArticle(Long id, WantedArticleRequest request) {
        WantedArticle article = wantedArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException());

        article.modifyArticle(request);
        return new GeneralResponseDto(200, "게시글이 수정되었습니다.");
    }

    public GeneralResponseDto addWantedBookmark(BookmarkRequestDto bookmarkRequestDto) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException());
        User user = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        wantedArticleBookmarkRepository.save(new WantedArticleBookmark(user, wantedArticle));
        return new GeneralResponseDto(200, "북마크에 추가 되었습니다.");
    }

    public GeneralResponseDto deleteWantedBookmark(BookmarkRequestDto bookmarkRequestDto) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException());
        User user = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());
        WantedArticleBookmark bookmark = wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, wantedArticle)
                .orElseThrow(() -> new BookmarkNotFoundException());

        wantedArticleBookmarkRepository.delete(bookmark);
        return new GeneralResponseDto(200, "북마크가 삭제되었습니다.");
    }
}
