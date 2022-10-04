package com.nextsquad.house.login.oauth;

import com.nextsquad.house.login.userinfo.UserInfo;

public interface OauthClient {

    UserInfo getUserInfo(String authCode);
}
