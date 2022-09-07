package com.nextsquad.house.acceptance.user;

import com.nextsquad.house.config.RestDocsConfiguration;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.UserInfoDto;
import com.nextsquad.house.exception.UserNotFoundException;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@Import(RestDocsConfiguration.class)
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserAcceptanceTest {
    private RequestSpecification spec;

    @Autowired
    private JwtProvider jwtProvider;

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    private JwtToken token;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        token = jwtProvider.createJwtToken(userRepository.findById(1L).orElseThrow(UserNotFoundException::new));
        RestAssured.port = port;
        spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("사용자 정보를 출력한다")
    void viewUserInfoTest() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("get-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
            .when()
                .get("/users/1")
            .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", equalTo(1))
                .body("accountId", equalTo("street62"))
                .body("displayName", equalTo("lee"))
                .body("profileImageUrl", equalTo("lucas.com"))
                .body("gender", equalTo("MALE"));
    }

    @Test
    @DisplayName("사용자 정보를 수정한다")
    void modifyUserInfoTest() {
        User savedUser = userRepository.save(new User());
        Long userId = savedUser.getId();
        UserInfoDto dto = new UserInfoDto(userId, "testName", "test.com", "MALE");
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("modify-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
                .body(dto)
            .when()
                .patch("/users/{userId}", userId)
            .then()
                .statusCode(HttpStatus.OK.value());

        userRepository.delete(savedUser);
    }

}
