package com.nextsquad.house.login.oauth;

import com.nextsquad.house.dto.login.KakaoAccessTokenResponseDto;
import com.nextsquad.house.dto.login.KakaoUserInfoDto;
import com.nextsquad.house.login.userinfo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Slf4j
public class KakaoOauthClient implements OauthClient {
    private final String clientId;
    private final String authServerUrl;
    private final String resourceServerUrl;
    private final String secretKey;
    private final WebClient webClient = WebClient.create();

    @Override
    public UserInfo getUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getOauthUserInfo(accessToken);
    }

    public KakaoOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        this.clientId = clientId;
        this.authServerUrl = authServerUrl;
        this.resourceServerUrl = resourceServerUrl;
        this.secretKey = secretKey;
    }

    private String getAccessToken(String authCode) {
        KakaoAccessTokenResponseDto rawToken = webClient.post()
                .uri(authServerUrl, uriBuilder -> uriBuilder
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_url", "http://52.79.243.28:8080/login/oauth/callback")
                        .queryParam("code", authCode)
                        .queryParam("grant_type", "authorization_code")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve()
                .bodyToFlux(KakaoAccessTokenResponseDto.class)
                .toStream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return parseToken(rawToken.getAccessToken());
    }

    private UserInfo getOauthUserInfo(String accessToken) {
        KakaoUserInfoDto infoDto = webClient.get()
                .uri(resourceServerUrl)
                .header("authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoUserInfoDto.class)
                .toStream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return infoDto.toUserInfo();
    }

    private String parseToken(String rawToken) {
        return String.format("Bearer %s", rawToken);
    }
}

