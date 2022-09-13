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
import static org.hamcrest.Matchers.hasSize;
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
        UserInfoDto dto = new UserInfoDto(1L, "testName", "test.com", "MALE");
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("modify-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
                .body(dto)
            .when()
                .patch("/users/{userId}", 1)
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("사용자의 양도 게시글을 출력한다")
    void viewUserRentArticleList() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("modify-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
            .when()
                .get("/users/{userId}/articles/rent", 1)
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("rentArticles", hasSize(12))
                .body("rentArticles[0].id", equalTo(1))
                .body("rentArticles[0].address", equalTo("서울특별시 성동구")) // 이미지 추가
                .body("rentArticles[0].availableFrom", equalTo("2022-08-01"))
                .body("rentArticles[0].contractType", equalTo("MONTHLY"))
                .body("rentArticles[0].createdAt", equalTo("2022-08-19T02:55:06.239433"))
                .body("rentArticles[0].deposit", equalTo(0))
                .body("rentArticles[0].completed", equalTo(false))
                .body("rentArticles[0].deleted", equalTo(false))
                .body("rentArticles[0].rentFee", equalTo(500000))
                .body("rentArticles[0].title", equalTo("왕십리역 원룸(1000/50)"))
                .body("rentArticles[0].bookmarked", equalTo(false));
    }

    @Test
    @DisplayName("사용자의 양수 게시글을 출력한다")
    void viewUserWantedArticleList() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("modify-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
            .when()
                .get("/users/{userId}/articles/wanted", 1)
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("wantedArticles", hasSize(12))
                .body("wantedArticles[0].id", equalTo(1))
                .body("wantedArticles[0].address", equalTo("서울특별시 성동구")) // 이미지 추가
                .body("wantedArticles[0].title", equalTo("왕십리역 원룸 구합니다"))
                .body("wantedArticles[0].content", equalTo("안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다."))
                .body("wantedArticles[0].moveInDate", equalTo("2022-08-01"))
                .body("wantedArticles[0].moveOutDate", equalTo("2023-02-02"))
                .body("wantedArticles[0].rentBudget", equalTo(550000))
                .body("wantedArticles[0].depositBudget", equalTo(10000000))
                .body("wantedArticles[0].createdAt", equalTo("2022-08-19T02:57:51"))
                .body("wantedArticles[0].bookmarked", equalTo(false));
    }

    @Test
    @DisplayName("사용자의 양도 게시글 북마크를 출력한다")
    void viewUserRentBookmarkList() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("modify-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
            .when()
                .get("/users/{userId}/bookmarks/rent", 1)
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("rentArticles", hasSize(2))
                .body("rentArticles[0].id", equalTo(13))
                .body("rentArticles[0].address", equalTo("서울특별시 성동구")) // 이미지 추가
                .body("rentArticles[0].availableFrom", equalTo("2022-08-01"))
                .body("rentArticles[0].contractType", equalTo("MONTHLY"))
                .body("rentArticles[0].createdAt", equalTo("2022-08-19T02:55:06.239433"))
                .body("rentArticles[0].deposit", equalTo(0))
                .body("rentArticles[0].completed", equalTo(false))
                .body("rentArticles[0].deleted", equalTo(false))
                .body("rentArticles[0].rentFee", equalTo(500000))
                .body("rentArticles[0].title", equalTo("왕십리역 원룸(1000/50)"))
                .body("rentArticles[0].bookmarked", equalTo(true));
    }

    @Test
    @DisplayName("사용자의 양수 게시글 북마크를 출력한다")
    void viewUserWantedBookmarkList() {
        RestAssured
            .given(spec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(RestAssuredRestDocumentation.document("modify-user-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .header("access-token", token.getAccessToken().getTokenCode())
            .when()
                .get("/users/{userId}/bookmarks/wanted", 1)
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("wantedArticles", hasSize(2))
                .body("wantedArticles[0].id", equalTo(13))
                .body("wantedArticles[0].address", equalTo("서울특별시 성동구")) // 이미지 추가
                .body("wantedArticles[0].title", equalTo("왕십리역 원룸 구합니다"))
                .body("wantedArticles[0].content", equalTo("안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다."))
                .body("wantedArticles[0].moveInDate", equalTo("2022-08-01"))
                .body("wantedArticles[0].moveOutDate", equalTo("2023-02-02"))
                .body("wantedArticles[0].rentBudget", equalTo(550000))
                .body("wantedArticles[0].depositBudget", equalTo(10000000))
                .body("wantedArticles[0].createdAt", equalTo("2022-08-19T02:57:51"))
                .body("wantedArticles[0].bookmarked", equalTo(true));
    }
}
