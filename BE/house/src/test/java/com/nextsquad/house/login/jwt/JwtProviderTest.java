package com.nextsquad.house.login.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.login.oauth.OauthClientType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProviderTest {
    private final JwtProvider jwtProvider = new JwtProvider("this_is_a_secret_key_for_jwt_provider_unit_test");
    private final User user = new User("sampleId", "sampleDisplayName", "profile.com", OauthClientType.KAKAO);

    @Test
    @DisplayName("createJwtToken()에 유저를 매개변수로 넣어 호출하면 해당 유저의 정보를 담은 JWT 토큰이 리턴된다.")
    void createJwtTokenTest() {
        JwtToken jwtToken = jwtProvider.createJwtToken(user);
        jwtProvider.verifyToken(jwtToken.getAccessToken().getTokenCode());
    }

    @Test
    @DisplayName("createRefreshedToken()에 유저와 액세스 토큰 매개변수로 넣어 호출하면 갱신된 JWT 토큰이 리턴된다")
    void createRefreshedTokenTest() {
        JwtToken originalToken = jwtProvider.createJwtToken(user);
        JwtToken refreshedToken = jwtProvider.createRefreshedToken(user, originalToken.getRefreshToken().getTokenCode());
    }

    @Test
    @DisplayName("verifyToken() 메서드에 유효한 JWT를 넣으면 예외 없이 통과된다")
    public void verifyTokenSuccessTest() {
        String validToken = jwtProvider.createJwtToken(user).getAccessToken().getTokenCode();
        jwtProvider.verifyToken(validToken);
    }

    @Test
    @DisplayName("verifyToken() 메서드에 유효하지 않은 JWT를 넣으면 예외가 발생한다")
    public void verifyTokenFailTest() {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        Assertions.assertThatThrownBy(() -> jwtProvider.verifyToken(invalidToken));
    }

    @Test
    @DisplayName("decode() 메서드를 호출해 추출한 내용은 생성 시 정의한 user의 필드와 일치한다")
    public void decodeTest() {
        JwtToken jwtToken = jwtProvider.createJwtToken(user);
        DecodedJWT decodedAccessToken = jwtProvider.decode(jwtToken.getAccessToken().getTokenCode());
        DecodedJWT decodedRefreshToken = jwtProvider.decode(jwtToken.getRefreshToken().getTokenCode());

        assertThat(decodedAccessToken.getClaim("tokenType").asString()).isEqualTo(JwtTokenType.ACCESS.name());
        assertThat(decodedRefreshToken.getClaim("tokenType").asString()).isEqualTo(JwtTokenType.REFRESH.name());

        assertThat(decodedAccessToken.getClaim("id").asLong()).isEqualTo(user.getId());
        assertThat(decodedAccessToken.getClaim("accountId").asString()).isEqualTo(user.getAccountId());
        assertThat(decodedAccessToken.getClaim("displayName").asString()).isEqualTo(user.getDisplayName());

        assertThat(decodedRefreshToken.getClaim("id").asLong()).isEqualTo(user.getId());
        assertThat(decodedRefreshToken.getClaim("accountId").asString()).isEqualTo(user.getAccountId());
        assertThat(decodedRefreshToken.getClaim("displayName").asString()).isEqualTo(user.getDisplayName());
    }
}