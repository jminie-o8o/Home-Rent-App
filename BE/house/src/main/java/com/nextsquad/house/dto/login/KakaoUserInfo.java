package com.nextsquad.house.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nextsquad.house.login.oauth.OauthClientType;
import com.nextsquad.house.login.userinfo.OauthUserInfo;

import java.util.Map;


public class KakaoUserInfo {
    @JsonProperty("kakao_account")
    private Map<String, Object> kakaoAccount;


    public OauthUserInfo toUserInfo() {
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");

        String email = String.valueOf(kakaoAccount.get("email"));
        String nickname = profile.get("nickname");
        String profileImageUrl = profile.get("profile_image_url");

        return new OauthUserInfo(email, nickname, profileImageUrl, OauthClientType.KAKAO);
    }
}
