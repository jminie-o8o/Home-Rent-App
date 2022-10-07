package com.nextsquad.house.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class OauthLoginRequestDto {
    @NotNull
    private String authCode;

    @NotNull
    private String oauthClientName;
}