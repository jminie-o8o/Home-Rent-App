package com.nextsquad.house.login.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class JwtToken {
    private String token;
    private Date expiresAt;
}
