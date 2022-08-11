package com.nextsquad.house.dto;

import com.nextsquad.house.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String displayName;
    private String profileImageUrl;
    private String gender;

    public static UserInfoDto from(User user) {
        return new UserInfoDto(user.getDisplayName(), user.getProfileImageUrl(), user.getGender().name());
    }
}
