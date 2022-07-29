package com.nextsquad.house.dto;

import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.jwt.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseDto {
    private Token accessToken;
    private Token refreshToken;

    public static JwtResponseDto from(JwtToken jwtToken) {
        return new JwtResponseDto(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
