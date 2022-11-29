package com.nextsquad.house.config;

import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.login.jwt.MockJwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JwtProviderConfiguration {
    @Value("${jwt.secret}")
    private String jwtSecret;


    @Profile("deploy")
    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(jwtSecret);
    }

    @Profile("dev")
    @Bean
    public JwtProvider mockJwtProvider() {
        return new MockJwtProvider(jwtSecret);
    }
}
