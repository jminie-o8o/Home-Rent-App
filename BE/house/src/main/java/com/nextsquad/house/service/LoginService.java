package com.nextsquad.house.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.JwtResponseDto;
import com.nextsquad.house.dto.OauthLoginRequestDto;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.jwt.JwtTokenType;
import com.nextsquad.house.login.oauth.OauthClient;
import com.nextsquad.house.login.oauth.OauthClientMapper;
import com.nextsquad.house.login.userinfo.UserInfo;
import com.nextsquad.house.repository.UserRefreshTokenRepository;
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
    private final UserRefreshTokenRepository refreshTokenRepository;

    public JwtResponseDto loginWithOauth(OauthLoginRequestDto requestDto) {
        OauthClient oauthClient = oauthClientMapper.getOauthClient(requestDto.getOauthClientName())
                .orElseThrow(() -> new RuntimeException());
        UserInfo userInfo = oauthClient.getUserInfo(requestDto.getAuthCode());

        User user = userRepository.findByAccountId(userInfo.getAccountId())
                .orElseGet(() -> registerUser(userInfo));

        JwtToken jwtToken = jwtProvider.createJwtToken(user);
        return JwtResponseDto.from(jwtToken);
    }

    private User registerUser(UserInfo userInfo) {
        User user = userInfo.toUser();
        return userRepository.save(user);
    }

    //TODO: Refresh token 관련 로직 작성
    public JwtResponseDto refreshJwtToken(String accessToken, String refreshToken) {
//        JwtToken refreshedToken = JwtProvider.refreshToken(accessToken, refreshToken);
        DecodedJWT decodedJWT = jwtProvider.verifyToken(accessToken);
        String payload = decodedJWT.getPayload();


        return null;
    }
}
