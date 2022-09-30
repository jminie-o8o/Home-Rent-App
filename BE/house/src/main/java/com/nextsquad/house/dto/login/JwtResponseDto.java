package com.nextsquad.house.dto.login;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.user.UserInfoDto;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.jwt.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseDto {
    private UserInfoDto user;
    private Token accessToken;
    private Token refreshToken;

    public static JwtResponseDto from(User user, JwtToken jwtToken) {
        return new JwtResponseDto(UserInfoDto.from(user), jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
