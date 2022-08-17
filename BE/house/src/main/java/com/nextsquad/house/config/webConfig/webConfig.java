package com.nextsquad.house.config.webConfig;

import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class webConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtProvider, redisService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**",  "/daum.html");
    }
}
