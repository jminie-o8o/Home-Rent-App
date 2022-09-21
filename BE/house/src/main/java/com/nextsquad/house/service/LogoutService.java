package com.nextsquad.house.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.login.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RedisService redisService;

    private final JwtProvider jwtProvider;

    public GeneralResponseDto logout(String accessToken, String refreshToken){
        DecodedJWT decodedJWT = jwtProvider.decode(accessToken);
        String accountId = decodedJWT.getClaim("accountId").asString();
        redisService.delete(accountId);
        redisService.save(accessToken, "abcdef", 15);
        return new GeneralResponseDto(200, "성공");
    }

}
