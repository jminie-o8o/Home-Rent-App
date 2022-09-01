package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
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
class WantedArticleBookmarkRepositoryTest {

    @Autowired
    private WantedArticleBookmarkRepository wantedArticleBookmarkRepository;

    @Autowired
    private WantedArticleRepository wantedArticleRepository;

    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    static class TestConfig {
        @PersistenceContext
        private EntityManager em;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(em);
        }
    }

    User user;
    Pageable pageable;
    WantedArticle wantedArticle;


    @BeforeEach
    void setup() {
        user = userRepository.save(new User("tester1", "tester", "image.com", OauthClientType.KAKAO));
        pageable = PageRequest.of(0, 5);
        wantedArticle = WantedArticle.builder()
                .user(user)
                .build();
        wantedArticleRepository.save(wantedArticle);

    }

    @Test
    @DisplayName("유저가 1개의 글을 찜했을 때 찜한 목록을 조회한다면 조회값이 1이어야한다")
    void findByUser() {
        //given 유저가 글을 찜한다
        wantedArticleBookmarkRepository.save(new WantedArticleBookmark(user, wantedArticle));

        //when 유저가 찜한 목록을 조회한다
        Page<WantedArticleBookmark> byUser = wantedArticleBookmarkRepository.findByUser(user, pageable);

        //then 유저가 찜한 게시글이 1개 이므로 엘리먼트가 1개여야한다
        assertThat(byUser.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저가 찜한 특정글을 조회한다")
    void findByUserAndWantedArticle() {
        //given 유저가 글을 찜한다
        wantedArticleBookmarkRepository.save(new WantedArticleBookmark(user, wantedArticle));

        //when 유저 찜한 특정글을 조회한다
        WantedArticleBookmark bookmark = wantedArticleBookmarkRepository.findByUserAndWantedArticle(user, wantedArticle).orElseThrow();

        //then 유저가 찜한 특정글의 아이디와 유저 아이디를 비교한다
        assertThat(bookmark.getWantedArticle().getId()).isEqualTo(wantedArticle.getId());
        assertThat(bookmark.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저가 찜한 글 목록을 리스트로 반환한다")
    void findListByUser() {
        //given 유저가 글을 찜한다
        wantedArticleBookmarkRepository.save(new WantedArticleBookmark(user, wantedArticle));

        //when 유저가 찜한 글 리스트를 조회한다
        List<WantedArticleBookmark> listByUser = wantedArticleBookmarkRepository.findListByUser(user);

        /*  then 반환 된 리스트의 사이즈는 1이어야 하고 리스트의 첫번째 요소의 원티드 아티클은 우리가 저장한 원티드
        아티클 아이디와 같아야 한다.*/
        assertThat(listByUser.size()).isEqualTo(1);
        assertThat(listByUser.get(0).getWantedArticle().getId()).isEqualTo(wantedArticle.getId());

    }
}

