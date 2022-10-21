package com.nextsquad.house.acceptance.rentArticle;

import com.nextsquad.house.dto.rentarticle.HouseFacilityList;
import com.nextsquad.house.dto.rentarticle.RentArticleRequest;
import com.nextsquad.house.dto.bookmark.BookmarkRequest;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.login.jwt.JwtToken;
import com.nextsquad.house.repository.user.UserRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Order(1)
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
                .body("rentArticles[0].id", equalTo(14))
                .body("rentArticles[0].title", equalTo("왕십리역 원룸(1000/50)"))
                .body("rentArticles[0].address", equalTo("서울특별시 성동구"))
                .body("rentArticles[0].houseImages", hasSize(1))
                .body("rentArticles[0].deposit", equalTo(0))
                .body("rentArticles[0].rentFee", equalTo(500000))
                .body("rentArticles[0].availableFrom", equalTo("2022-08-01"))
                .body("rentArticles[0].bookmarkCount", equalTo(1))
                .body("rentArticles[0].contractExpiresAt", equalTo("2023-02-02"))
                .body("rentArticles[0].createdAt", equalTo("2022-08-19T02:57:46.239433"))
                .body("rentArticles[0].completed", equalTo(false))
                .body("rentArticles[0].deleted", equalTo(false))
                .body("rentArticles[0].bookmarked", equalTo(true))
                .body("rentArticles[0].bookmarkCount", equalTo(1))
                .body("hasNext", equalTo(true));
    }
    @Test
    void id가_12번인_양도글을_상세조회한다(){
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
                .body("deposit", equalTo(0))
                .body("rentFee", equalTo(650000))
                .body("maintenanceFee", equalTo(50000))
                .body("maintenanceFeeDescription", equalTo("수도, 가스 별도"))
                .body("availableFrom", equalTo("2022-08-01"))
                .body("contractExpiresAt", equalTo("2023-02-02"))
                .body("bookmarkCount", equalTo(0))
                .body("maxFloor", equalTo(4))
                .body("thisFloor", equalTo(2))
                .body("houseFacility.hasParkingLot", equalTo(true))
                .body("houseFacility.hasBalcony", equalTo(true))
                .body("houseFacility.hasElevator", equalTo(true))
                .body("houseFacility.hasAircon", equalTo(true))
                .body("houseFacility.hasLaundry", equalTo(false))
                .body("houseFacility.hasBed", equalTo(false))
                .body("houseFacility.hasFridge", equalTo(false))
                .body("houseFacility.hasTv", equalTo(false))
                .body("houseFacility.hasCctv", equalTo(true))
                .body("houseFacility.hasVideoPhone", equalTo(true))
                .body("houseFacility.hasLobby", equalTo(false))
                .body("houseImages", hasSize(2))
                .body("createdAt", equalTo("2022-08-19T02:57:20.736606"))
                .body("modifiedAt", equalTo("2022-08-19T02:56:10.736613"))
                .body("completed", equalTo(false))
                .body("bookmarked", equalTo(false));
    }

    @Test
    void id가_11번인_양도글을_수정한다(){
        List<String> images = new ArrayList<>();

        HouseFacilityList facility = new HouseFacilityList(true, true, true, true, true, false, false, false, false, false, false);
        RentArticleRequest request = new RentArticleRequest("대전 서구 둔산동", "둔산 하이츠",
                "갤러리아 백화점 5분거리", 115.323, 221.3432, "급히 방 양도합니다.", "사정이 생겨 양도합니다",
                "MONTHLY", "ONEROOM", 1000000, 300000,
                140000, "전기,수도,인터넷 등 모두 포함", LocalDate.of(2022, 9, 20),
                LocalDate.of(2023, 4,10), 10, 5, images, facility);

        given(documentationSpec)
                .filter(document("modify-rent-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .patch("/houses/rent/11")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("게시글이 수정되었습니다."));
    }

    @Test
    void id가_11번인_양도글을_거래완료_처리한다(){
        given(documentationSpec)
                .filter(document("complete-rent-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .patch("/houses/rent/11/isCompleted")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("게시글 상태가 변경되었습니다."));
    }

    @Test
    void id가_11번인_양도글을_삭제한다(){
        given(documentationSpec)
                .filter(document("delete-rent-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .delete("/houses/rent/11")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("게시글이 삭제되었습니다."));
    }

    @Test
    void 양도글을_작성하고_저장하면_글id_13번을_리턴한다(){
        List<String> images = new ArrayList<>();
        HouseFacilityList facility = new HouseFacilityList(true, true, true, true, true, false, false, false, false, false, false);

        RentArticleRequest request = new RentArticleRequest("대전 서구 둔산동", "둔산 하이츠",
                "갤러리아 백화점 5분거리", 115.323, 221.3432, "급히 방 양도합니다.", "사정이 생겨 양도합니다",
                "MONTHLY", "ONEROOM", 1000000, 300000,
                140000, "전기,수도,인터넷 등 모두 포함", LocalDate.of(2022, 9, 20),
                LocalDate.of(2023, 4,10), 10, 5, images, facility);

        given(documentationSpec)
                .filter(document("write-rent-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .post("houses/rent")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", equalTo(15));
    }

    @Order(2)
    @Test
    void id가_1번인_사용자가_id_10번_양도글을_북마크에_추가한다(){
        BookmarkRequest request = new BookmarkRequest(10L);

        given(documentationSpec)
                .filter(document("add-bookmark-rent-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .post("houses/rent/bookmarks")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("북마크에 추가 되었습니다."));
    }

    @Test
    void id가_1번인_사용자가_id_10번_양도글을_북마크에서_해제한다() {
        BookmarkRequest request = new BookmarkRequest(10L);
        given(documentationSpec)
                .filter(document("delete-bookmark-rent-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .delete("houses/rent/bookmarks")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("북마크가 삭제되었습니다."));
    }


    //Restdocs 생성하지 않는 테스트(예외 확인용 테스트)

    @Test
    void 다른_유저의_게시글을_수정하면_예외가_발생한다(){
        List<String> images = new ArrayList<>();
        HouseFacilityList facility = new HouseFacilityList(true, true, true, true, true, false, false, false, false, false, false);
        RentArticleRequest request = new RentArticleRequest("대전 서구 둔산동", "둔산 하이츠",
                "갤러리아 백화점 5분거리", 115.323, 221.3432, "급히 방 양도합니다.", "사정이 생겨 양도합니다",
                "MONTHLY", "ONEROOM", 1000000, 300000,
                140000, "전기,수도,인터넷 등 모두 포함", LocalDate.of(2022, 9, 20),
                LocalDate.of(2023, 4,10), 10, 5, images, facility);
        given(documentationSpec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .patch("/houses/rent/13")

                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .assertThat()
                .body("code", equalTo(401))
                .body("message", equalTo("접근 권한이 없습니다."));
    }
    @Test
    void 다른_유저의_게시글을_완료_처리하면_예외가_발생한다(){
        given(documentationSpec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .patch("/houses/rent/13/isCompleted")

                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .assertThat()
                .body("code", equalTo(401))
                .body("message", equalTo("접근 권한이 없습니다."));
    }

    @Test
    void 다른_유저의_게시글을_삭제하면_예외가_발생한다(){
        given(documentationSpec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .delete("/houses/rent/13")

                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .assertThat()
                .body("code", equalTo(401))
                .body("message", equalTo("접근 권한이 없습니다."));
    }

    @Test
    void 사용자가_삭제된_11번_양도글을_북마크에_추가하면_예외가_발생한다() {
        BookmarkRequest request = new BookmarkRequest( 11L);
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .post("houses/rent/bookmarks")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Order(3)
    @Test
    void 사용자가_이미_북마크에_존재하는_양도글을_다시_추가하면_예외가_발생한다(){
        BookmarkRequest request = new BookmarkRequest(10L);
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .post("houses/rent/bookmarks")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 존재하지_않는_북마크를_삭제하면_예외가_발생한다(){
        BookmarkRequest request = new BookmarkRequest(9L);
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .delete("houses/rent/bookmarks")

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 존재하지_않는_양도글을_북마크에서_삭제하면_예외가_발생한다(){
        BookmarkRequest request = new BookmarkRequest(18L);
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .delete("houses/rent/bookmarks")

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 존재하지_않는_양도글을_상세조회_하면_예외가_발생한다(){
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .get("houses/rent/22")

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
