package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.repository.rentarticle.RentArticleBookmarkRepository;
import com.nextsquad.house.repository.rentarticle.RentArticleRepository;
import com.nextsquad.house.repository.user.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RentArticleBookmarkRepositoryTest {

    @Autowired
    RentArticleBookmarkRepository rentArticleBookmarkRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentArticleRepository rentArticleRepository;

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
    RentArticle rentArticle;
    Pageable pageable;

    @BeforeEach
    void setup() {
        user = userRepository.findById(1L).orElseThrow();
        pageable = PageRequest.of(0, 5);
        rentArticle = rentArticleRepository.findById(10L).orElseThrow();
    }

    @Test
    @DisplayName("유저의 찜리스트를 반환한다")
    void findByUser() {
        //given 유저가 양도글 한 개를 찜한다.
        Page<RentArticleBookmark> before = rentArticleBookmarkRepository.findByUser(user, pageable);
        rentArticleBookmarkRepository.save(new RentArticleBookmark(rentArticle, user));

        //when 유저가 찜한 글들을 조회한다.
        Page<RentArticleBookmark> byUser = rentArticleBookmarkRepository.findByUser(user, pageable);

        //then 유저의 기존 찜 글은 2개였으나 given에서 한 개 더 저장했으므로 엘리먼트의 개수는 이전 값 보다 1 커야 한다.
        assertThat(byUser.getTotalElements()).isEqualTo(before.getTotalElements() + 1);
    }

    @Test
    @DisplayName("유저와 양도글을 기준으로 조회했을 때 조회 결과가 존재하면 True를 반환한다")
    void findByUserAndRentArticle() {
        //given 유저가 양도글 한 개를 찜한다.
        rentArticleBookmarkRepository.save(new RentArticleBookmark(rentArticle, user));

        //when 유저와 양도글을 기준으로 조회한다.
        Optional<RentArticleBookmark> result = rentArticleBookmarkRepository.findByUserAndRentArticle(user, rentArticle);

        //then 조회 결과가 존재하는지 여부를 판단할 때 true를 반환한다.
        assertThat(result.isPresent()).isEqualTo(true);
    }

    @Test
    @DisplayName("유저가 찜한 양도글 리스트를 조회한다")
    void findListByUser() {
        //given 유저가 양도글 하나를 찜한다.
        int before = rentArticleBookmarkRepository.findListByUser(user).size();
        rentArticleBookmarkRepository.save(new RentArticleBookmark(rentArticle, user));

        //when 유저의 양도글 찜 리스트를 조회한다.
        List<RentArticleBookmark> listByUser = rentArticleBookmarkRepository.findListByUser(user);

        //then 유저의 양도글 찜 리스트 길이는 before보다 1 커야 하며 리스트의 마지막 글은 given에서 저장한 글이어야 한다.
        assertThat(listByUser.size()).isEqualTo(before + 1);
        assertThat(listByUser.get(2).getRentArticle().getId()).isEqualTo(rentArticle.getId());
    }

    @Test
    @DisplayName("양도글을 찜 리스트에서 삭제한다")
    void deleteByRentArticle() {
        //given 양도글(13L) 하나가 삭제 된다.
        RentArticle article = rentArticleRepository.findById(13L).orElseThrow();
        article.markAsDeleted();

        //when rentArticleBookmark에 있는 해당 양도글을 삭제한다.
        rentArticleBookmarkRepository.deleteByRentArticle(article);

        //then 양도글(13L)을 찜했던 유저의 찜여부를 조회하면 비어있다.
        Optional<RentArticleBookmark> bookmark = rentArticleBookmarkRepository.findByUserAndRentArticle(user, article);
        assertThat(bookmark.isPresent()).isEqualTo(false);

    }
}
