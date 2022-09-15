package com.nextsquad.house.acceptance.login;

import com.nextsquad.house.config.RestDocsConfiguration;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.OauthLoginRequestDto;
import com.nextsquad.house.exception.UserNotFoundException;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.login.oauth.*;
import com.nextsquad.house.login.userinfo.UserInfo;
import com.nextsquad.house.repository.UserRepository;
import com.nextsquad.house.service.RedisService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@Import(RestDocsConfiguration.class)
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LoginAcceptanceTest {
    private RequestSpecification spec;
    @LocalServerPort
    private int port;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private OauthClientMapper oauthClientMapper;
    @MockBean
    private RedisService redisService;

    private JwtToken token;
    private User user;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        user = new User("testId", "testName", "lucas.com", OauthClientType.KAKAO);
        token = jwtProvider.createJwtToken(userRepository.findById(1L).orElseThrow(UserNotFoundException::new));
        RestAssured.port = port;
        spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("Oauth 서버에서 발급받은 authCode로 로그인을 요청하면 OK 응답이 온다.")
    void registerUserTest() {
        KakaoOauthClient kakaoClient = Mockito.mock(KakaoOauthClient.class);
        Mockito.when(kakaoClient.getUserInfo("authCode")).thenReturn(new UserInfo(user.getAccountId(), user.getDisplayName(), user.getProfileImageUrl(), user.getOauthClientType()));
        Mockito.when(oauthClientMapper.getOauthClient("KAKAO")).thenReturn(Optional.of(kakaoClient));

        OauthLoginRequestDto requestDto = new OauthLoginRequestDto("authCode", "KAKAO");

        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("get-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .body(requestDto)
            .when()
                .post("/login/oauth")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log();
    }

    @Test
    @DisplayName("헤더에 access-token과 refresh-token을 넣어 로그아웃 요청을 보내면 OK 응답이 온다")
    void logoutTest() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("get-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
                .header("refresh-token", token.getRefreshToken().getTokenCode())
            .when()
                .post("/logout")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log();
    }

    @Test
    @DisplayName("이미 DB에 있는 닉네임을 넣고 중복검사를 요청하면 true가 응답된다")
    void duplicateTrueTest() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("get-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .param("nickname", "lee")
                .header("access-token", token.getAccessToken().getTokenCode())
            .when()
                .get("/users/check-duplication")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("isDuplicated", is(true));
    }

    @Test
    @DisplayName("DB에 없는 닉네임을 넣고 중복검사를 요청하면 false가 응답된다")
    void duplicateFalseTest() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("get-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .param("nickname", "testnickname")
                .header("access-token", token.getAccessToken().getTokenCode())
            .when()
                .get("/users/check-duplication")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("isDuplicated", is(false));
    }

    @Test
    @DisplayName("만료된 access token과 만료되지 않은 refresh token을 헤더에 넣어 갱신을 요청하면 토큰이 갱신된다")
    void refreshTest() {
        Mockito.when(redisService.get(anyString())).thenReturn(token.getRefreshToken().getTokenCode());

        RestAssured
                .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("get-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
                .header("refresh-token", token.getRefreshToken().getTokenCode())
            .when()
                .post("/login/refresh")
            .then()
                .statusCode(HttpStatus.OK.value())
                .log();
    }
}
