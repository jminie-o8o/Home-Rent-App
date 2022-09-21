package com.nextsquad.house.login.oauth;

import com.nextsquad.house.login.userinfo.UserInfo;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

public abstract class OauthClient {
    protected String clientId;
    protected String authServerUrl;
    protected String resourceServerUrl;
    protected String secretKey;
    protected WebClient webClient = WebClient.create();

    protected OauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        this.clientId = clientId;
        this.authServerUrl = authServerUrl;
        this.resourceServerUrl = resourceServerUrl;
        this.secretKey = secretKey;
    }

    public UserInfo getUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getOauthUserInfo(accessToken);
    }
    protected abstract UserInfo getOauthUserInfo(String accessToken);



    protected abstract String getAccessToken(String authCode);

    protected abstract String parseToken(String rawToken);
}
