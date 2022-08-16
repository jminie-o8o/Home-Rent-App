package com.nextsquad.house.service;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.dto.wantedArticle.WantedArticleElementResponse;
import com.nextsquad.house.dto.wantedArticle.SavedWantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
import com.nextsquad.house.dto.wantedArticle.WantedArticleResponse;
import com.nextsquad.house.repository.UserRepository;
import com.nextsquad.house.repository.WantedArticleBookmarkRepository;
import com.nextsquad.house.repository.WantedArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WantedArticleService {

    private final WantedArticleRepository wantedArticleRepository;
    private final UserRepository userRepository;
    private final WantedArticleBookmarkRepository wantedArticleBookmarkRepository;

    public SavedWantedArticleResponse writeWantedArticle(WantedArticleRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
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
                .orElseThrow(() -> new IllegalArgumentException("요청하신 id에 해당하는 게시글이 없습니다."));
        article.addViewCount();
        return WantedArticleResponse.from(article);
    }
    
    public WantedArticleListResponse getWantedArticleList(Pageable pageable) {
        Page<WantedArticle> articles = wantedArticleRepository.findByAvailable(pageable);
        List<WantedArticleElementResponse> elementResponseList = articles.stream().map(WantedArticleElementResponse::from).collect(Collectors.toList());
        boolean hasNext = pageable.getPageNumber() < articles.getTotalPages() - 1;
        return new WantedArticleListResponse(elementResponseList, hasNext);
    }


    public GeneralResponseDto deleteWantedArticle(Long id) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당하는 ID의 게시글이 존재하지 않습니다"));
        wantedArticle.markAsDeleted();
        wantedArticleRepository.save(wantedArticle);
        return new GeneralResponseDto(200, "게시글이 삭제되었습니다.");
    }
    
    public GeneralResponseDto updateWantedArticle(Long id, WantedArticleRequest request) {
        WantedArticle article = wantedArticleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 id에 해당하는 게시글이 없습니다."));

        article.modifyArticle(request);
        return new GeneralResponseDto(200, "게시글이 수정되었습니다.");
    }

    public GeneralResponseDto addWantedBookmark(BookmarkRequestDto bookmarkRequestDto) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당하는 ID의 게시글이 존재하지 않습니다"));
        User user = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다"));

        wantedArticleBookmarkRepository.save(new WantedArticleBookmark(user, wantedArticle));
        return new GeneralResponseDto(200, "북마크에 추가 되었습니다.");
    }

    public GeneralResponseDto deleteWantedBookmark(BookmarkRequestDto bookmarkRequestDto) {
        WantedArticle wantedArticle = wantedArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당하는 ID의 게시글이 존재하지 않습니다"));
        User user = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다"));
        WantedArticleBookmark bookmark = wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, wantedArticle)
                .orElseThrow(() -> new RuntimeException("북마크가 존재하지 않습니다."));

        wantedArticleBookmarkRepository.delete(bookmark);
        return new GeneralResponseDto(200, "북마크가 삭제되었습니다.");
    }
}
