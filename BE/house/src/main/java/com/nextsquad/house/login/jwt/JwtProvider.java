package com.nextsquad.house.login.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtProvider {

    private final Key key;

    public JwtProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtToken createJwtToken(User user) {
        Token accessToken = createToken(user, JwtTokenType.ACCESS);
        Token refreshToken = createToken(user, JwtTokenType.REFRESH);
        return new JwtToken(accessToken, refreshToken);
    }

    private Token createToken(User user, JwtTokenType tokenType) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + tokenType.getDuration());
        String token = Jwts.builder()
                .setIssuer("codesquad-team-5")
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .claim("tokenType", tokenType.name())
                .claim("id", user.getId())
                .claim("accountId", user.getAccountId())
                .claim("displayName", user.getDisplayName())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return new Token(token, expiresAt);
    }

    public boolean validateToken(String token) {
        return parseClaims(token) != null;
    }

    public DecodedJWT verifyToken(String token){
        JWTVerifier verifier = JWT.require()
                .withIssuer("codesquad-team-5")
                .acceptExpiresAt(900000)
                .build();
        return verifier.verify(token);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("⚠️Invalid JWT signature.");
            throw new RuntimeException();
        } catch (MalformedJwtException e) {
            log.info("⚠️Invalid JWT token.");
            throw new RuntimeException();
        } catch (ExpiredJwtException e) {
            // TODO : 만료된 토큰 처리
            log.info("⚠️Expired JWT token.");
            throw new RuntimeException();
        } catch (UnsupportedJwtException e) {
            log.info("⚠️Unsupported JWT token.");
            throw new RuntimeException();
        } catch (IllegalArgumentException e) {
            log.info("⚠️JWT token compact of handler are invalid.");
            throw new RuntimeException();
        }
    }
}