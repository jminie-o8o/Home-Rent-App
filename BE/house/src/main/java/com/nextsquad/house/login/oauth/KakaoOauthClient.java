package com.nextsquad.house.login.oauth;

import com.google.gson.Gson;
import com.nextsquad.house.dto.KakaoAccessTokenResponseDto;
import com.nextsquad.house.dto.KakaoUserInfoDto;
import com.nextsquad.house.dto.NaverUserInfoDto;
import com.nextsquad.house.login.userinfo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;

@Slf4j
public class KakaoOauthClient extends OauthClient {
    public KakaoOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        super(clientId, authServerUrl, resourceServerUrl, secretKey);
    }

    @Override
    protected String getAccessToken(String authCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl(authServerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        KakaoAccessTokenResponseDto rawToken = webClient.post()
                .uri(uriBuilder -> uriBuilder
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
        WebClient webClient = WebClient.create();
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

    protected UserInfo convertToUserInfoFrom(String rawInfo) {
        Map<String, String> infoMap = new Gson().fromJson(rawInfo, Map.class);
        return new UserInfo(infoMap.get("email"), infoMap.get("nickname"), null, OauthClientType.KAKAO);
    }
}

