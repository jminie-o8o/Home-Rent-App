package com.nextsquad.house.login.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.login.oauth.OauthClientType;
import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;
//    private String jwtSecret = "codesquadhonux220629wefhkufhkwefjwlfjwlfijwlj%60%60";

    @BeforeEach
    void init() {
//        jwtProvider = new JwtProvider(jwtSecret);
    }

    @Test
    void verifyTokenTest() {
        JwtToken jwtToken = jwtProvider.createJwtToken(new User("sampleId", "sampleName", OauthClientType.GITHUB));
        String tokenCode = jwtToken.getAccessToken().getTokenCode();

        DecodedJWT decodedJWT = jwtProvider.verifyToken(tokenCode);

        Claims claims = jwtProvider.parseClaims(tokenCode);
        String tokenType = claims.get("tokenType", String.class);

        assertThat(tokenType).isEqualTo("ACCESS");
    }
}