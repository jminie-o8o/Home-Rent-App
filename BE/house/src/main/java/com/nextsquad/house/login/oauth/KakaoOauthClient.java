package com.nextsquad.house.login.oauth;

import com.nextsquad.house.dto.login.KakaoAccessTokenResponseDto;
import com.nextsquad.house.dto.login.KakaoUserInfoDto;
import com.nextsquad.house.login.userinfo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Slf4j
public class KakaoOauthClient extends OauthClient {
    public KakaoOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        super(clientId, authServerUrl, resourceServerUrl, secretKey);
    }

    @Override
    protected String getAccessToken(String authCode) {
        KakaoAccessTokenResponseDto rawToken = webClient.post()
                .uri(authServerUrl, uriBuilder -> uriBuilder
                        .queryParam("client_id", "9dc5e51153cd29428199781510c17a32")
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
                .orElseThrow(() -> new RuntimeException());
        return parseToken(rawToken.getAccessToken());
    }

    @Override
    protected UserInfo getOauthUserInfo(String accessToken) {
        KakaoUserInfoDto infoDto = webClient.get()
                .uri(resourceServerUrl)
                .header("authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoUserInfoDto.class)
                .toStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        return infoDto.toUserInfo();
    }

    @Override
    protected String parseToken(String rawToken) {
        return String.format("Bearer %s", rawToken);
    }
}

