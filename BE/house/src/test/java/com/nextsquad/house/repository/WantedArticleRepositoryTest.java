package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.SearchConditionDto;
import com.nextsquad.house.login.oauth.OauthClientType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WantedArticleRepositoryTest {
    @Autowired
    private WantedArticleRepository wantedArticleRepository;
    @Autowired
    private UserRepository userRepository;
    User writer;
    User writer2;
    Pageable pageable;

    @TestConfiguration
    static class TestConfig {
        @PersistenceContext
        private EntityManager em;
        @Bean
        public JPAQueryFactory jpaQueryFactory(){
            return new JPAQueryFactory(em);
        }
    }

    @BeforeEach
    void setup() {
        User user = new User("tester1", "tester", "image.com", OauthClientType.KAKAO);
        User user1 = new User("tester2", "tester2", "image2.com", OauthClientType.KAKAO);
        writer = userRepository.save(user);
        writer2 = userRepository.save(user1);
        pageable = PageRequest.of(0, 5);
    }

    @Test
    @DisplayName("유저가 10개의 글을 작성했을 때 글 목록 조회시 삭제되지 않은 10개의 글이 조회된다")
    void findByUser() {
        //given wantedArticle 저장

        for (int i = 0; i < 10; i++) {
            WantedArticle wantedArticle = WantedArticle.builder()
                    .user(writer)
                    .title("제목")
                    .content("날짜")
                    .build();
            wantedArticleRepository.save(wantedArticle);
        }
        WantedArticle wantedArticle = wantedArticleRepository.findById(writer.getId()).orElseThrow();
        wantedArticle.markAsDeleted();
        wantedArticleRepository.save(wantedArticle);

        //when
        Page<WantedArticle> byUser = wantedArticleRepository.findByUser(writer, pageable);

        //then 목록 확인
        assertThat(byUser.getTotalElements()).isEqualTo(9);
    }

    @Test
    @DisplayName("양도글에 존재하지 않는 키워드로 검색시 리턴값(리스트의 사이즈)이 0이 된다")
    void findByKeyword() {
        //given wantedArticle 저장
        WantedArticle wantedArticle = WantedArticle.builder()
                .user(writer)
                .title("서울 원룸")
                .address("서울 강남구")
                .build();
        wantedArticleRepository.save(wantedArticle);

        //when
        SearchConditionDto searchCondition = new SearchConditionDto(null, null, "대전");
        List<WantedArticle> byKeyword = wantedArticleRepository.findByKeyword(searchCondition, pageable);

        //then 검색어와 실제 글 확인
        assertThat(byKeyword.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("2명의 유저가 각각 10개의 글을 썼을 때 글 전체 목록 조회시 20개의 글이 반환된다")
    void findAll(){
        //given wantedArticle 저장
        for (int i = 0; i < 10; i++) {
            WantedArticle wantedArticle = WantedArticle.builder()
                    .user(writer)
                    .title("제목")
                    .content("날짜")
                    .build();
            wantedArticleRepository.save(wantedArticle);
            WantedArticle article = WantedArticle.builder()
                    .user(writer2)
                    .title("제목1")
                    .content("날짜1")
                    .build();
            wantedArticleRepository.save(article);
        }

        //when 게시글 목록 조회
        Page<WantedArticle> all = wantedArticleRepository.findAll(pageable);

        //then 게시글 갯수 확인
        assertThat(all.getTotalElements()).isEqualTo(20);
    }
}
