package com.nextsquad.house.config;

import com.nextsquad.house.login.oauth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class OauthClientConfiguration {
    private final OauthProperties githubProperties;
    private final OauthProperties kakaoProperties;
    private final OauthProperties naverProperties;

    @Bean
    public OauthClientMapper oauthClientMapper() {
        GithubOauthClient github = new GithubOauthClient(githubProperties.getClientId(), githubProperties.getAuthServerUrl(), githubProperties.getResourceServerUrl(), githubProperties.getSecretKey());
        KakaoOauthClient kakao = new KakaoOauthClient(kakaoProperties.getClientId(), kakaoProperties.getAuthServerUrl(), kakaoProperties.getResourceServerUrl(), kakaoProperties.getSecretKey());
        NaverOauthClient naver = new NaverOauthClient(naverProperties.getClientId(), naverProperties.getAuthServerUrl(), naverProperties.getResourceServerUrl(), naverProperties.getSecretKey());

        Map<String, OauthClient> clientMap = new HashMap<>();

        clientMap.put("GITHUB", github);
        clientMap.put("KAKAO", kakao);
        clientMap.put("NAVER", naver);

        return new OauthClientMapper(clientMap);
    }
}
