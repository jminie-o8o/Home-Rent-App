package com.nextsquad.house.dto;

import com.nextsquad.house.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String accountId;
    private String displayName;
    private String profileImageUrl;
    private String gender;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getAccountId(), user.getDisplayName(), user.getProfileImageUrl(), user.getGender().name());
    }
}
