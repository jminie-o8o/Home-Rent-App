package com.nextsquad.house.login.oauth;

import com.nextsquad.house.dto.login.NaverAccessTokenResponseDto;
import com.nextsquad.house.dto.login.NaverUserInfoDto;
import com.nextsquad.house.login.userinfo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Slf4j
public class NaverOauthClient implements OauthClient {
    private final String clientId;
    private final String authServerUrl;
    private final String resourceServerUrl;
    private final String secretKey;
    private final WebClient webClient = WebClient.create();

    @Value("${spring.oauth.naver.state}")
    private String state;

    public NaverOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        this.clientId = clientId;
        this.authServerUrl = authServerUrl;
        this.resourceServerUrl = resourceServerUrl;
        this.secretKey = secretKey;
    }

    @Override
    public UserInfo getUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getOauthUserInfo(accessToken);
    }

    private String getAccessToken(String authCode) {
        NaverAccessTokenResponseDto rawToken = webClient.post()
                .uri(authServerUrl, uriBuilder -> uriBuilder
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", secretKey)
                        .queryParam("code", authCode)
                        .queryParam("state", state)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve()
                .bodyToFlux(NaverAccessTokenResponseDto.class)
                .toStream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return parseToken(rawToken.getAccessToken());
    }

    private UserInfo getOauthUserInfo(String accessToken) {
        NaverUserInfoDto infoDto = webClient.get()
                .uri(resourceServerUrl)
                .header("authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(NaverUserInfoDto.class)
                .toStream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return infoDto.toUserInfo();
    }

    private String parseToken(String rawToken) {
        return String.format("Bearer %s", rawToken);
    }

}
