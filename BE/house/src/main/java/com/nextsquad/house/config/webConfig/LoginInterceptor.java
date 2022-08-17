package com.nextsquad.house.config.webConfig;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("access-token");

        if (accessToken == null || redisService.get(accessToken) != null) {
            throw new JWTDecodeException("JWT 토큰이 올바르지 않습니다.");
        }
        jwtProvider.verifyToken(accessToken);
        return true;
    }
}
