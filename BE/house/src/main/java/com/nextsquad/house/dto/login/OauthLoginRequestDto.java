package com.nextsquad.house.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthLoginRequestDto {
    private String authCode;
    private String oauthClientName;
}