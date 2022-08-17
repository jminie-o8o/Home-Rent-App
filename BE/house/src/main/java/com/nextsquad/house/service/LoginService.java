package com.nextsquad.house.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.JwtResponseDto;
import com.nextsquad.house.dto.OauthLoginRequestDto;
import com.nextsquad.house.exception.OauthClientNotFoundException;
import com.nextsquad.house.exception.UserNotFoundException;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.oauth.OauthClient;
import com.nextsquad.house.login.oauth.OauthClientMapper;
import com.nextsquad.house.login.userinfo.UserInfo;
import com.nextsquad.house.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final OauthClientMapper oauthClientMapper;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public JwtResponseDto loginWithOauth(OauthLoginRequestDto requestDto) {
        log.info("login started!");
        OauthClient oauthClient = oauthClientMapper.getOauthClient(requestDto.getOauthClientName())
                .orElseThrow(() -> new OauthClientNotFoundException());
        UserInfo userInfo = oauthClient.getUserInfo(requestDto.getAuthCode());

        User user = userRepository.findByAccountId(userInfo.getAccountId())
                .orElseGet(() -> registerUser(userInfo));

        JwtToken jwtToken = jwtProvider.createJwtToken(user);
        log.info("saving refresh token... key: {}, value: {}", user.getAccountId(), jwtToken.getRefreshToken().getTokenCode());
        redisService.save(user.getAccountId(), jwtToken.getRefreshToken().getTokenCode());
        return JwtResponseDto.from(jwtToken);
    }

    private User registerUser(UserInfo userInfo) {
        User user = userInfo.toUser();
        return userRepository.save(user);
    }

    //TODO: Refresh token 관련 로직 작성
    public JwtResponseDto refreshJwtToken(String accessToken, String refreshToken) {
        jwtProvider.verifyToken(accessToken);
        DecodedJWT decode = jwtProvider.decode(refreshToken);
        String accountId = decode.getClaim("accountId").asString();

        log.info(accountId);

        String storedRefreshToken = redisService.get(accountId);
        log.info("stored refreshToken: {}", storedRefreshToken);
        validateRefreshToken(refreshToken, storedRefreshToken);

        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserNotFoundException());
        JwtToken newJwtToken = jwtProvider.createRefreshedToken(user, refreshToken);

        return new JwtResponseDto(newJwtToken.getAccessToken(), newJwtToken.getRefreshToken());

    }

    private void validateRefreshToken(String refreshToken, String storedRefreshToken) {
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new RuntimeException();
        }
    }
}

