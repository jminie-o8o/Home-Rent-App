package com.nextsquad.house.config;

import com.nextsquad.house.login.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtProviderConfiguration {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(jwtSecret);
    }
}
