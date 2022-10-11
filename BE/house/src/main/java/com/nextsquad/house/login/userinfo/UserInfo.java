package com.nextsquad.house.login.userinfo;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.login.oauth.OauthClientType;
import lombok.Getter;

import java.util.Random;

@Getter
public class UserInfo {
    protected String accountId;
    protected String displayName;

    protected String profileImageUrl;
    private OauthClientType oauthClientType;
    private static final String DEFAULT_PROFILE_IMAGE = "https://house-image-bucket.s3.ap-northeast-2.amazonaws.com/default_profile.png";

    public UserInfo(String accountId, String displayName, String profileImageUrl, OauthClientType oauthClientType) {
        String identifier = String.valueOf(new Random().nextInt(9999));

        this.accountId = accountId;
        this.displayName = displayName + identifier;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : DEFAULT_PROFILE_IMAGE;
        this.oauthClientType = oauthClientType;
    }

    public User toUser() {
        return new User(accountId, displayName, profileImageUrl, oauthClientType);
    }
}
