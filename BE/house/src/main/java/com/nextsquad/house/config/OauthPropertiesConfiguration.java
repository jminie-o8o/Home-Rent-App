package com.nextsquad.house.config;

import com.nextsquad.house.login.oauth.OauthProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OauthPropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "oauth.github")
    public OauthProperties githubProperties() {
        return new OauthProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "oauth.kakao")
    public OauthProperties kakaoProperties() {
        return new OauthProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "oauth.naver")
    public OauthProperties naverProperties() {
        return new OauthProperties();
    }
}

