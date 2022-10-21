package com.nextsquad.house.dto.user;

import com.nextsquad.house.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {
    private Long userId;
    private String displayName;
    private String profileImageUrl;
    private String gender;

    public static UserInfo from(User user) {
        if (user.getGender() != null) {
            return new UserInfo(user.getId(), user.getDisplayName(), user.getProfileImageUrl(), String.valueOf(user.getGender()));
        }
        return new UserInfo(user.getId(), user.getDisplayName(), user.getProfileImageUrl(), null);
    }
}
