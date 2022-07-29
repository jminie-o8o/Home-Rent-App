package com.nextsquad.house.login.oauth;

import com.nextsquad.house.dto.NaverAccessTokenResponseDto;
import com.nextsquad.house.dto.NaverUserInfoDto;
import com.nextsquad.house.login.userinfo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Slf4j
public class NaverOauthClient extends OauthClient{

    @Value("${spring.oauth.naver.state}")
    private String state;

    public NaverOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        super(clientId, authServerUrl, resourceServerUrl, secretKey);
    }

    @Override
    protected String getAccessToken(String authCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl(authServerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        NaverAccessTokenResponseDto rawToken = webClient.post()
                .uri(uriBuilder -> uriBuilder
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
                .orElseThrow(() -> new RuntimeException());
        return parseToken(rawToken.getAccessToken());
    }

    @Override
    protected String getOauthResponse(String accessToken) {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(resourceServerUrl)
                .header("authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(String.class)
                .toStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    protected UserInfo getOauthUserInfo(String accessToken) {
        WebClient webClient = WebClient.create();
        NaverUserInfoDto infoDto = webClient.get()
                .uri(resourceServerUrl)
                .header("authorization", accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(NaverUserInfoDto.class)
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
