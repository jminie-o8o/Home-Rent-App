package com.nextsquad.house.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.login.JwtResponse;
import com.nextsquad.house.dto.login.OauthLoginRequest;
import com.nextsquad.house.exception.OauthClientNotFoundException;
import com.nextsquad.house.exception.UserNotFoundException;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.oauth.OauthClient;
import com.nextsquad.house.login.oauth.OauthClientMapper;
import com.nextsquad.house.login.userinfo.OauthUserInfo;
import com.nextsquad.house.repository.user.UserRepository;
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

    public JwtResponse loginWithOauth(OauthLoginRequest requestDto) {
        log.info("login started!");
        OauthClient oauthClient = oauthClientMapper.getOauthClient(requestDto.getOauthClientName())
                .orElseThrow(() -> new OauthClientNotFoundException());
        OauthUserInfo oauthUserInfo = oauthClient.getUserInfo(requestDto.getAuthCode());

        User user = userRepository.findByAccountId(oauthUserInfo.getAccountId())
                .orElseGet(() -> registerUser(oauthUserInfo));

        JwtToken jwtToken = jwtProvider.createJwtToken(user);
        log.info("saving refresh token... key: {}, value: {}", user.getAccountId(), jwtToken.getRefreshToken().getTokenCode());
        redisService.save(user.getAccountId(), jwtToken.getRefreshToken().getTokenCode());
        return JwtResponse.from(user, jwtToken);
    }

    private User registerUser(OauthUserInfo oauthUserInfo) {
        User user = oauthUserInfo.toUser();
        return userRepository.save(user);
    }

    public JwtResponse refreshJwtToken(String accessToken, String refreshToken) {
        DecodedJWT decode = jwtProvider.decode(refreshToken);
        String accountId = decode.getClaim("accountId").asString();

        log.info(accountId);

        String storedRefreshToken = redisService.get(accountId);
        log.info("stored refreshToken: {}", storedRefreshToken);
        validateRefreshToken(refreshToken, storedRefreshToken);

        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserNotFoundException());
        JwtToken newJwtToken = jwtProvider.createRefreshedToken(user, refreshToken);

        return JwtResponse.from(user, newJwtToken);

    }

    private void validateRefreshToken(String refreshToken, String storedRefreshToken) {
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new RuntimeException();
        }
    }
}

