package com.nextsquad.house.acceptance.wantedArticle;

import com.nextsquad.house.dto.bookmark.BookmarkRequest;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@Import(RestDocsAutoConfiguration.class)
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("양수글 인수 테스트")
public class WantedArticleAcceptanceTest {

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
    void 삭제되지_않은_전체_양수글을_조회한다() {

        given(documentationSpec)
                .filter(document("get-wanted-article-list", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .queryParam("page", 0)
                .queryParam("size", 1)

                .when()
                .get("/houses/wanted")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("wantedArticles", hasSize(1))
                .body("wantedArticles[0].id", equalTo(14))
                .body("wantedArticles[0].address", equalTo("서울특별시 성동구"))
                .body("wantedArticles[0].title", equalTo("왕십리역 원룸 구합니다"))
                .body("wantedArticles[0].content", equalTo("안녕하세요. 갑자기 왕십리 쪽에 살 일이 있어서 급하게 집 구합니다."))
                .body("wantedArticles[0].moveInDate", equalTo("2022-08-01"))
                .body("wantedArticles[0].moveOutDate", equalTo("2023-02-02"))
                .body("wantedArticles[0].rentBudget", equalTo(550000))
                .body("wantedArticles[0].depositBudget", equalTo(10000000))
                .body("wantedArticles[0].createdAt", equalTo("2022-08-19T02:59:52"))
                .body("wantedArticles[0].bookmarked", equalTo(true))
                .body("wantedArticles[0].bookmarkCount", equalTo(1))
                .body("hasNext", equalTo(true));
    }

    @Test
    void id가_7번인_양수글을_상세조회한다() {
        given(documentationSpec)
                .filter(document("get-wanted-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .get("/houses/wanted/7")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", equalTo(7))
                .log();
    }

    @Test
    void id가_1번인_양수글을_삭제한다(){
        given(documentationSpec)
                .filter(document("delete-wanted-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .delete("houses/wanted/1")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("게시글이 삭제되었습니다."));
    }

    @Test
    void 다른_유저의_게시글을_삭제하면_예외가_발생한다(){
        given(documentationSpec)
                .filter(document("delete-wanted-article-error", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())

                .when()
                .delete("houses/wanted/13")

                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .assertThat()
                .body("code", equalTo(401))
                .body("message", equalTo("접근 권한이 없습니다."));
    }

    @Test
    void id가_2번인_양수글을_수정한다(){
        WantedArticleRequest request = new WantedArticleRequest("주소 수정 테스트", "제목 수정 테스트"
        , "내용 수정 테스트", LocalDate.now(), LocalDate.now(), 500, 20000);
        given(documentationSpec)
                .filter(document("update-wanted-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .patch("houses/wanted/2")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("게시글이 수정되었습니다."));
    }

    @Test
    void 다른_유저의_게시글을_수정하면_예외가_발생한다(){
        WantedArticleRequest request = new WantedArticleRequest("주소 수정 테스트", "제목 수정 테스트"
                , "내용 수정 테스트", LocalDate.now(), LocalDate.now(), 500, 20000);
        given(documentationSpec)
                .filter(document("update-wanted-article-error", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .patch("houses/wanted/13")

                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .assertThat()
                .body("code", equalTo(401))
                .body("message", equalTo("접근 권한이 없습니다."));
    }

    @Test
    void 양수글을_작성하고_저장했을_때_글번호_13을_리턴한다(){
        WantedArticleRequest request = new WantedArticleRequest("글작성 테스트 주소",
                "글쓰기 테스트", "양도글 작성 테스트 본문", LocalDate.of(2022, 11, 12), LocalDate.of(2023, 11, 12), 100, 100);
        given(documentationSpec)
                .filter(document("write-wanted-article", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .post("houses/wanted")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", equalTo(15));
    }

    @Order(1)
    @Test
    void 양수글_한개를_북마크에_저장한다(){
        BookmarkRequest request = new BookmarkRequest(1L);
        given(documentationSpec)
                .filter(document("save-wanted-article-bookmark", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .post("houses/wanted/bookmarks")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("북마크에 추가 되었습니다."));
    }
    @Order(2)
    @Test
    void 저장한_양수글_북마크_한개를_삭제한다(){
        BookmarkRequest request = new BookmarkRequest(1L);
        given(documentationSpec)
                .filter(document("delete-wanted-article-bookmark", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .delete("houses/wanted/bookmarks")

                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("code", equalTo(200))
                .body("message", equalTo("북마크가 삭제되었습니다."));
    }

    @Test
    void 삭제된_글을_북마크에_추가하려하면_예외가_발생한다(){
        BookmarkRequest request = new BookmarkRequest(1L);
        given(documentationSpec)
                .filter(document("add-bookmark-error", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("access-token", jwtToken.getAccessToken().getTokenCode())
                .body(request)

                .when()
                .post("houses/wanted/bookmarks")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
