package com.nextsquad.house.login.userinfo;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.login.oauth.OauthClientType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfo {
    protected String accountId;
    protected String displayName;
    private OauthClientType oauthClientType;

    public User toUser() {
        return new User(accountId, displayName, oauthClientType);
    }
}
