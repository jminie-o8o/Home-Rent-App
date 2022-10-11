package com.nextsquad.house.dto.user;

import com.nextsquad.house.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String accountId;
    private String displayName;
    private String profileImageUrl;
    private String gender;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getAccountId(), user.getDisplayName(), user.getProfileImageUrl(), user.getGender().name());
    }
}
