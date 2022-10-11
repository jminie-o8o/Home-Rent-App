package com.nextsquad.house.login.oauth;

import com.nextsquad.house.login.userinfo.OauthUserInfo;

public interface OauthClient {

    OauthUserInfo getUserInfo(String authCode);
}
