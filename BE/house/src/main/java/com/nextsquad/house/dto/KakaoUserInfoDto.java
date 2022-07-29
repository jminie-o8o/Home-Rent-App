package com.nextsquad.house.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nextsquad.house.login.oauth.OauthClientType;
import com.nextsquad.house.login.userinfo.UserInfo;
import lombok.NoArgsConstructor;

import java.util.Map;


public class KakaoUserInfoDto {
    @JsonProperty("kakao_account")
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> profile;


    public UserInfo toUserInfo() {
        this.profile = (Map<String, Object>) kakaoAccount.get("profile");
        String email = String.valueOf(kakaoAccount.get("email"));
        String nickname = String.valueOf(profile.get("nickname"));
        String profileImageUrl = String.valueOf(profile.get("profile_image_url"));

        return new UserInfo(email, nickname, profileImageUrl, OauthClientType.KAKAO);
    }
}
