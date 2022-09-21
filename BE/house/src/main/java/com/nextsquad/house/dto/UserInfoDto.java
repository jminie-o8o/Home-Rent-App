package com.nextsquad.house.dto;

import com.nextsquad.house.domain.user.Gender;
import com.nextsquad.house.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long userId;
    private String displayName;
    private String profileImageUrl;
    private String gender;

    public static UserInfoDto from(User user) {
        if (user.getGender() != null) {
            return new UserInfoDto(user.getId(), user.getDisplayName(), user.getProfileImageUrl(), String.valueOf(user.getGender()));
        }
        return new UserInfoDto(user.getId(), user.getDisplayName(), user.getProfileImageUrl(), null);
    }
}
