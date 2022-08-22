package com.nextsquad.house.dto;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.jwt.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseDto {
    private Long userId;
    private Token accessToken;
    private Token refreshToken;

    public static JwtResponseDto from(User user, JwtToken jwtToken) {
        return new JwtResponseDto(user.getId(), jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
