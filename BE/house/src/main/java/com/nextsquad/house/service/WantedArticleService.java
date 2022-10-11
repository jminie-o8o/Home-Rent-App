package com.nextsquad.house.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.dto.SearchConditionDto;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.dto.wantedArticle.SavedWantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
import com.nextsquad.house.dto.wantedArticle.WantedArticleResponse;
import com.nextsquad.house.exception.*;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.repository.user.UserRepository;
import com.nextsquad.house.repository.wantedarticle.WantedArticleBookmarkRepository;
import com.nextsquad.house.repository.wantedarticle.WantedArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class WantedArticleService {

    private final WantedArticleRepository wantedArticleRepository;
    private final UserRepository userRepository;
    private final WantedArticleBookmarkRepository wantedArticleBookmarkRepository;
    private final JwtProvider jwtProvider;

    public SavedWantedArticleResponse writeWantedArticle(WantedArticleRequest request, String token) {
        User user = getUserFromAccessToken(token);
        WantedArticle wantedArticle = generateWantedArticle(request, user);

        wantedArticleRepository.save(wantedArticle);
        return new SavedWantedArticleResponse(wantedArticle.getId());
    }


    public WantedArticleResponse getWantedArticle(Long articleId, String token) {
        User user = getUserFromAccessToken(token);

        WantedArticle article = wantedArticleRepository.findById(articleId)
                .orElseThrow(ArticleNotFoundException::new);
        if (article.isDeleted()) {
            throw new IllegalArgumentException("삭제된 글 입니다.");
        }

        boolean isBookmarked = wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, article).isPresent();

        article.addViewCount();
        return new WantedArticleResponse(article, isBookmarked);
    }

    public WantedArticleListResponse getWantedArticleList(SearchConditionDto searchCondition, Pageable pageable, String token) {

        User user = getUserFromAccessToken(token);
        List<WantedArticleBookmark> listByUser = wantedArticleBookmarkRepository.findListByUser(user);
        Map<Long, Boolean> bookmarkHashMap = getBookmarkArticleMap(listByUser);

        List<WantedArticle> wantedArticles = wantedArticleRepository.findByKeyword(searchCondition, pageable);
        boolean hasNext = checkHasNext(pageable, wantedArticles);

        return WantedArticleListResponse.of(wantedArticles, bookmarkHashMap, hasNext);
    }


    public GeneralResponseDto deleteWantedArticle(Long id, String token) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        authorizeArticleOwner(token, wantedArticle);

        wantedArticle.markAsDeleted();
        wantedArticleBookmarkRepository.deleteByWantedArticle(wantedArticle);
        return new GeneralResponseDto(200, "게시글이 삭제되었습니다.");
    }

    public GeneralResponseDto updateWantedArticle(Long id, WantedArticleRequest request, String token) {
        WantedArticle article = wantedArticleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        authorizeArticleOwner(token, article);

        article.modifyArticle(request);
        return new GeneralResponseDto(200, "게시글이 수정되었습니다.");
    }

    public GeneralResponseDto addWantedBookmark(BookmarkRequestDto bookmarkRequestDto, String token) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(ArticleNotFoundException::new);

        User user = getUserFromAccessToken(token);

        if (wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, wantedArticle).isPresent()) {
            throw new DuplicateBookmarkException();
        }

        checkIsAvailable(wantedArticle);

        wantedArticleBookmarkRepository.save(new WantedArticleBookmark(user, wantedArticle));
        return new GeneralResponseDto(200, "북마크에 추가 되었습니다.");
    }

    public GeneralResponseDto deleteWantedBookmark(BookmarkRequestDto bookmarkRequestDto, String token) {
        User user = getUserFromAccessToken(token);

        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(ArticleNotFoundException::new);
        WantedArticleBookmark bookmark = wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, wantedArticle)
                .orElseThrow(BookmarkNotFoundException::new);

        wantedArticleBookmarkRepository.delete(bookmark);
        return new GeneralResponseDto(200, "북마크가 삭제되었습니다.");
    }

    private void checkIsAvailable(WantedArticle wantedArticle) {
        if (wantedArticle.isDeleted()) {
            throw new IllegalArgumentException("삭제된 게시글은 추가할 수 없습니다.");
        }
        if (wantedArticle.isCompleted()) {
            throw new IllegalArgumentException("삭제된 게시글은 추가할 수 없습니다.");
        }
    }

    private void authorizeArticleOwner(String token, WantedArticle article) {
        Long loggedInId = jwtProvider.decode(token).getClaim("id").asLong();
        User user = userRepository.findById(loggedInId)
                .orElseThrow(UserNotFoundException::new);

        if (!user.equals(article.getUser())) {
            throw new AccessDeniedException();
        }
    }

    private User getUserFromAccessToken(String token) {
        DecodedJWT decode = jwtProvider.decode(token);
        Long id = decode.getClaim("id").asLong();
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return user;
    }

    private boolean checkHasNext(Pageable pageable, List<WantedArticle> wantedArticles) {
        boolean checkHasNext = pageable.getPageSize() < wantedArticles.size();
        if (checkHasNext) {
            wantedArticles.remove(wantedArticles.size() - 1);
        }
        return checkHasNext;
    }

    private Map<Long, Boolean> getBookmarkArticleMap(List<WantedArticleBookmark> listByUser) {
        Map<Long, Boolean> bookmarkHashMap = new HashMap<Long, Boolean>();
        for (WantedArticleBookmark wantedArticleBookmark : listByUser) {
            bookmarkHashMap.put(wantedArticleBookmark.getWantedArticle().getId(), true);
        }
        return bookmarkHashMap;
    }

    private WantedArticle generateWantedArticle(WantedArticleRequest request, User user) {
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
        return wantedArticle;
    }
}
