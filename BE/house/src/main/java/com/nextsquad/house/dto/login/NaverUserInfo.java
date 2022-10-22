package com.nextsquad.house.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nextsquad.house.login.oauth.OauthClientType;
import com.nextsquad.house.login.userinfo.OauthUserInfo;
import lombok.Getter;

import java.util.Map;

@Getter

public class NaverUserInfo {

    @JsonProperty("response")
    private Map<String, String> response;

    public OauthUserInfo toUserInfo() {
        return new OauthUserInfo(response.get("email"), response.get("nickname"), response.get("profile_image"), OauthClientType.NAVER);
    }
}
