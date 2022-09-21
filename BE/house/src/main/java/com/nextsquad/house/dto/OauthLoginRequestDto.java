package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthLoginRequestDto {
    private String authCode;
    private String oauthClientName;
}