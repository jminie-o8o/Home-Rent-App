package com.nextsquad.house.acceptance.rentArticle;

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
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@Import(RestDocsAutoConfiguration.class)
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("양도글 인수 테스트")
public class RentArticleAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    RequestSpecification documentationSpec;
    JwtToken jwtToken;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        documentationSpec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
        jwtToken = jwtProvider.createJwtToken(userRepository.findById(1L).get());
    }

    @Test
    void 삭제되지_않은_양도글의_목록을_조회한다(){
        given(documentationSpec)
                .filter(document("get-rent-article-list", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .queryParam("page", 0)
                .queryParam("size", 1)

                .when()
                .get("/houses/rent")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("rentArticles", hasSize(1))
                .body("rentArticles[0].id", equalTo(9))
                .body("rentArticles[0].title", equalTo("성동구 원룸(1000/45)"))
                .body("rentArticles[0].address", equalTo("서울특별시 성동구"))
                .body("rentArticles[0].houseImages", hasSize(2))
                .body("rentArticles[0].deposit", equalTo(0))
                .body("rentArticles[0].rentFee", equalTo(450000))
                .body("rentArticles[0].availableFrom", equalTo("2022-08-01"))
                .body("rentArticles[0].bookmarkCount", equalTo(0))
                .body("rentArticles[0].contractExpiresAt", equalTo("2023-02-02"))
                .body("rentArticles[0].createdAt", equalTo("2022-08-19T02:56:10.736606"))
                .body("rentArticles[0].completed", equalTo(false))
                .body("rentArticles[0].deleted", equalTo(false))
                .body("rentArticles[0].bookmarked", equalTo(false))
                .body("hasNext", equalTo(true));
    }

    @Test
    void id가_번인_양도글을_상세조회한다(){
        given(documentationSpec)
                .filter(document("get-rent-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .get("/houses/rent/12")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", equalTo(12))
                .body("user.userId", equalTo(1))
                .body("user.displayName", equalTo("lee"))
                .body("user.profileImageUrl", equalTo("lucas.com"))
                .body("user.gender", equalTo("MALE"))
                .body("address", equalTo("서울특별시 성동구"))
                .body("addressDetail", equalTo("왕십리로22길 22"))
                .body("addressDescription", equalTo("왕십리역 코앞"))
                .body("latitude", equalTo(110.1F))
                .body("longitude", equalTo(1010.1231F))
                .body("title", equalTo("왕십리 원룸(1000/65)"))
                .body("content", equalTo("사정이 있어서 계약기간 못 채우고 양도합니다."))
                .body("contractType", equalTo("MONTHLY"))
                .body("houseType", equalTo("ONEROOM"))
                .body("facilities", hasSize(0))
                .body("deposit", equalTo(0))
                .body("rentFee", equalTo(650000))
                .body("maintenanceFee", equalTo(50000))
                .body("maintenanceFeeDescription", equalTo("수도, 가스 별도"))
                .body("availableFrom", equalTo("2022-08-01"))
                .body("contractExpiresAt", equalTo("2023-02-02"))
                .body("bookmarkCount", equalTo(0))
                .body("maxFloor", equalTo(4))
                .body("thisFloor", equalTo(2))
                .body("hasParkingLot", equalTo(true))
                .body("hasBalcony", equalTo(false))
                .body("hasElevator", equalTo(true))
                .body("houseImages", hasSize(2))
                .body("securityFacilities", hasSize(0))
                .body("createdAt", equalTo("2022-08-19T02:56:10.736606"))
                .body("modifiedAt", equalTo("2022-08-19T02:56:10.736613"))
                .body("completed", equalTo(false))
                .body("bookmarked", equalTo(false));
    }

}
