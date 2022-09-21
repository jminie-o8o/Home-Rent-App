package com.nextsquad.house.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nextsquad.house.login.oauth.OauthClientType;
import com.nextsquad.house.login.userinfo.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;


import java.util.Map;

@Getter

public class NaverUserInfoDto {

    @JsonProperty("response")
    private Map<String, String> response;

    public UserInfo toUserInfo() {
        return new UserInfo(response.get("email"), response.get("nickname"), response.get("profile_image"), OauthClientType.NAVER);
    }
}
