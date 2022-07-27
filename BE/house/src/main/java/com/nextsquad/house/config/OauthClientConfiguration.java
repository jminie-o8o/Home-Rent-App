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

    @Bean
    public OauthClientMapper oauthClientMapper() {
        GithubOauthClient github = new GithubOauthClient(githubProperties.getClientId(), githubProperties.getAuthServerUrl(), githubProperties.getResourceServerUrl(), githubProperties.getSecretKey());
        KakaoOauthClient kakao = new KakaoOauthClient(kakaoProperties.getClientId(), kakaoProperties.getAuthServerUrl(), kakaoProperties.getResourceServerUrl(), kakaoProperties.getSecretKey());

        Map<String, OauthClient> clientMap = new HashMap<>();

        clientMap.put("GITHUB", github);
        clientMap.put("KAKAO", kakao);

        return new OauthClientMapper(clientMap);
    }
}
