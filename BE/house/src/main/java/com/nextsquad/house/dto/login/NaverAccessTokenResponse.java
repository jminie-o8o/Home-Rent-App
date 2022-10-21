package com.nextsquad.house.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaverAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
