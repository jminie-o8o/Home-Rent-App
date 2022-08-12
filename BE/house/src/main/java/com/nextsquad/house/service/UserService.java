package com.nextsquad.house.service;

import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.*;
import com.nextsquad.house.dto.user.DuplicationCheckResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleElementResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.repository.RentArticleBookmarkRepository;
import com.nextsquad.house.repository.UserRepository;
import com.nextsquad.house.repository.WantedArticleBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RentArticleBookmarkRepository rentArticleBookmarkRepository;
    private final WantedArticleBookmarkRepository wantedArticleBookmarkRepository;

    public UserResponseDto getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("없는 유저 입니다."));
        return UserResponseDto.from(user);
    }

    public GeneralResponseDto modifyUserInfo(Long id, UserInfoDto userInfoDto){
        User user = userRepository.findById(id).orElseThrow();
        if (userRepository.existsUserByDisplayName(userInfoDto.getDisplayName())) {
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }
        user.modifyInfo(userInfoDto);
        return new GeneralResponseDto(200, "정보가 수정되었습니다");
    }

    public RentArticleListResponse getRentBookmark(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        List<RentArticleBookmark> bookmarks = rentArticleBookmarkRepository.findByUser(user);
        List<RentArticleListElement> elements = bookmarks.stream().map(RentArticleListElement::from).collect(Collectors.toList());
        return new RentArticleListResponse(elements);
    }

    public DuplicationCheckResponse checkDuplication(String nickname) {
        return new DuplicationCheckResponse(userRepository.existsUserByDisplayName(nickname));
    }

    public WantedArticleListResponse getWantedBookmark(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당하는 ID의 사용자가 없습니다"));
        List<WantedArticleBookmark> bookmarks = wantedArticleBookmarkRepository.findByUser(user);
        List<WantedArticleElementResponse> elements = bookmarks.stream().map(WantedArticleElementResponse::from).collect(Collectors.toList());
        return new WantedArticleListResponse(elements);

    }
}
