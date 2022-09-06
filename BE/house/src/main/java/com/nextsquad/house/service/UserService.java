package com.nextsquad.house.service;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.*;
import com.nextsquad.house.dto.user.DuplicationCheckResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleElementResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.exception.UserNotFoundException;
import com.nextsquad.house.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RentArticleBookmarkRepository rentArticleBookmarkRepository;
    private final WantedArticleBookmarkRepository wantedArticleBookmarkRepository;
    private final WantedArticleRepository wantedArticleRepository;
    private final RentArticleRepository rentArticleRepository;

    public UserResponseDto getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return UserResponseDto.from(user);
    }

    public GeneralResponseDto modifyUserInfo(Long id, UserInfoDto userInfoDto){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (checkDuplicated(userInfoDto, user)) {
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }
        user.modifyInfo(userInfoDto);
        return new GeneralResponseDto(200, "정보가 수정되었습니다");
    }

    private boolean checkDuplicated(UserInfoDto userInfoDto, User user) {
        return !user.getDisplayName().equals(userInfoDto.getDisplayName()) && userRepository.existsUserByDisplayName(userInfoDto.getDisplayName());
    }

    public RentArticleListResponse getRentBookmark(long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        Page<RentArticleBookmark> bookmarks = rentArticleBookmarkRepository.findByUser(user, pageable);
        List<RentArticleListElement> elements = bookmarks.stream()
                .map(RentArticleListElement::from)
                .peek(element -> element.setBookmarked(true))
                .collect(Collectors.toList());
        return new RentArticleListResponse(elements, hasNext(pageable, bookmarks));
    }

    public DuplicationCheckResponse checkDuplication(String nickname) {
        return new DuplicationCheckResponse(userRepository.existsUserByDisplayName(nickname));
    }

    public WantedArticleListResponse getWantedBookmark(long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        Page<WantedArticleBookmark> bookmarks = wantedArticleBookmarkRepository.findByUser(user, pageable);
        List<WantedArticleElementResponse> elements = bookmarks.stream()
                .map(WantedArticleElementResponse::from)
                .peek(element -> element.setBookmarked(true))
                .collect(Collectors.toList());

        return new WantedArticleListResponse(elements, hasNext(pageable, bookmarks));

    }

    public RentArticleListResponse getMyRentArticles(long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        Page<RentArticle> rentArticles = rentArticleRepository.findByUser(user, pageable);
        List<RentArticleListElement> responseElements = rentArticles.stream()
                .map(RentArticleListElement::from)
                .collect(Collectors.toList());

        return new RentArticleListResponse(responseElements, hasNext(pageable, rentArticles));
    }
    
    public WantedArticleListResponse getMyWantedArticles(long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        Page<WantedArticle> articles = wantedArticleRepository.findByUser(user, pageable);
        List<WantedArticleElementResponse> myArticles = articles.stream().map(WantedArticleElementResponse::from).collect(Collectors.toList());

        return new WantedArticleListResponse(myArticles, hasNext(pageable, articles));
    }

    private boolean hasNext(Pageable pageable, Page<?> articles) {
        return pageable.getPageNumber() < articles.getTotalPages() - 1;
    }
}
