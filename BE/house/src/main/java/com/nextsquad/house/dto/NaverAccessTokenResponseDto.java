package com.nextsquad.house.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaverAccessTokenResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
}
