package com.nextsquad.house.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


import java.util.Map;

@Getter

public class NaverUserInfo {

    @JsonProperty("response")
    private Map<String, String> response;

}
