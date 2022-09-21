package com.nextsquad.house.login.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Token {
    private String tokenCode;
    private Date expiresAt;
}
