package com.nextsquad.house.dto.login;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.user.UserInfo;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.jwt.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private UserInfo user;
    private Token accessToken;
    private Token refreshToken;

    public static JwtResponse from(User user, JwtToken jwtToken) {
        return new JwtResponse(UserInfo.from(user), jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
