package com.nextsquad.house.login.oauth;

import com.google.gson.Gson;
import com.nextsquad.house.dto.NaverAccessTokenResponseDto;
import com.nextsquad.house.dto.NaverUserInfo;
import com.nextsquad.house.login.userinfo.UserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;

public class NaverOauthClient extends OauthClient{

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
                .bodyToFlux(NaverUserInfo.class)
                .toStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    protected String parseToken(String rawToken) {
        return String.format("Bearer %s", rawToken);
    }

    @Override
    protected UserInfo convertToUserInfoFrom(String rawInfo) {
        Map<String, String> infoMap = new Gson().fromJson(rawInfo, Map.class);
        return new UserInfo(infoMap.get("login"), infoMap.get("login"), OauthClientType.NAVER);
    }
}
