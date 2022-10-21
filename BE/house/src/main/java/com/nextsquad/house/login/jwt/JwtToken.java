package com.nextsquad.house.login.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JwtToken {
    private Token accessToken;
    private Token refreshToken;
}
