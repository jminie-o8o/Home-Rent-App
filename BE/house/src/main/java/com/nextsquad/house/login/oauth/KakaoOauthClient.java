package com.nextsquad.house.login.oauth;

import com.nextsquad.house.dto.login.KakaoAccessTokenResponse;
import com.nextsquad.house.dto.login.KakaoUserInfo;
import com.nextsquad.house.login.userinfo.OauthUserInfo;
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

    public KakaoOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        this.clientId = clientId;
        this.authServerUrl = authServerUrl;
        this.resourceServerUrl = resourceServerUrl;
        this.secretKey = secretKey;
    }

    @Override
    public OauthUserInfo getUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        return getOauthUserInfo(accessToken);
    }

    private String getAccessToken(String authCode) {
        KakaoAccessTokenResponse rawToken = webClient.post()
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
                .bodyToFlux(KakaoAccessTokenResponse.class)
                .toStream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return parseToken(rawToken.getAccessToken());
    }

    private OauthUserInfo getOauthUserInfo(String accessToken) {
        KakaoUserInfo infoDto = webClient.get()
                .uri(resourceServerUrl)
                .header("authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoUserInfo.class)
                .toStream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return infoDto.toUserInfo();
    }

    private String parseToken(String rawToken) {
        return String.format("Bearer %s", rawToken);
    }
}

