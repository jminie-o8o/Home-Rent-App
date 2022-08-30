package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.SearchConditionDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RentArticleRepositoryTest {
    @Autowired
    private RentArticleRepository rentArticleRepository;

    @Autowired
    private UserRepository userRepository;


    @TestConfiguration
    static class TestConfig {
        @PersistenceContext
        private EntityManager em;
        @Bean
        public JPAQueryFactory jpaQueryFactory(){
            return new JPAQueryFactory(em);
        }
    }

    @Test
    @DisplayName("양도글 작성 후 save()를 호출하면 저장 완료된 게시글을 반환해야 한다")
    void addRentArticle() {
        RentArticle article = new RentArticle();
        RentArticle savedArticle = rentArticleRepository.save(article);
        assertThat(savedArticle).isEqualTo(article);
    }

    @Test
    @DisplayName("findById()를 호출하면 해당 ID에 맞는 게시글을 반환해야 한다")
    void findRentArticleById() {
        RentArticle article = new RentArticle();
        rentArticleRepository.save(article);

        RentArticle foundArticle = rentArticleRepository.findById(article.getId())
                .orElse(new RentArticle());

        assertThat(foundArticle).isEqualTo(article);
    }

    @Test
    @DisplayName("findByUser()를 호출하면 해당 유저의 게시글을 반환해야 한다")
    void findByUserTest() {
        User user = new User();
        userRepository.save(user);

        List<RentArticle> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RentArticle article = RentArticle.builder()
                    .user(user)
                    .build();
            articles.add(article);
        }
        rentArticleRepository.saveAll(articles);

        PageRequest pageable = PageRequest.of(0, 5);
        Page<RentArticle> pages = rentArticleRepository.findByUser(user, pageable);

        List<RentArticle> collect = pages.get().collect(Collectors.toList());

        for (int i = 0; i < 5; i++) {
            assertThat(collect.get(i)).isEqualTo(articles.get(i));
            assertThat(collect.get(i).getUser()).isEqualTo(user);
        }

        assertThat(pages.hasNext()).isTrue();
        pageable = pageable.next();
        pages = rentArticleRepository.findByUser(user, pageable);

        collect = pages.get().collect(Collectors.toList());

        for (int i = 0; i < 5; i++) {
            assertThat(collect.get(i)).isEqualTo(articles.get(i + 5));
            assertThat(collect.get(i).getUser()).isEqualTo(user);
        }

        assertThat(pages.hasNext()).isFalse();
    }

    @Test
    @DisplayName("findByKeyword() 월세 순 정렬 테스트")
    public void sortedByRentFeeTest() {
        List<RentArticle> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RentArticle article = RentArticle.builder()
                    .rentFee(1000000 - (i * 1000))
                    .build();
            articles.add(article);
        }
        rentArticleRepository.saveAll(articles);

        SearchConditionDto condition = new SearchConditionDto(null, "rentFee", null);

        int previousRentFee = Integer.MIN_VALUE;
        PageRequest pageable = PageRequest.of(0, 10);

        List<RentArticle> foundArticles = rentArticleRepository.findByKeyword(condition, pageable);
        for (RentArticle foundArticle : foundArticles) {
            assertThat(foundArticle.getRentFee()).isGreaterThanOrEqualTo(previousRentFee);
            previousRentFee = foundArticle.getRentFee();
        }
    }

    @Test
    @DisplayName("findByKeyword() 보증금 순 정렬 테스트")
    public void sortedByDepositTest() {
        List<RentArticle> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RentArticle article = RentArticle.builder()
                    .deposit(1000000 - (i * 1000))
                    .build();
            articles.add(article);
        }
        rentArticleRepository.saveAll(articles);

        SearchConditionDto condition = new SearchConditionDto(null, "deposit", null);

        int previousDeposit = Integer.MIN_VALUE;
        PageRequest pageable = PageRequest.of(0, 10);

        List<RentArticle> foundArticles = rentArticleRepository.findByKeyword(condition, pageable);
        for (RentArticle foundArticle : foundArticles) {
            assertThat(foundArticle.getDeposit()).isGreaterThanOrEqualTo(previousDeposit);
            previousDeposit = foundArticle.getDeposit();
        }
    }

    @Test
    @DisplayName("findByKeyword() 거래 가능만 보기 필터링 테스트")
    public void filteredByAvailableOnlyTest() {
        List<RentArticle> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RentArticle article = RentArticle.builder()
                    .isCompleted(i > 5)
                    .build();
            articles.add(article);
        }
        rentArticleRepository.saveAll(articles);

        SearchConditionDto condition = new SearchConditionDto(true, null, null);

        PageRequest pageable = PageRequest.of(0, 10);

        List<RentArticle> foundArticles = rentArticleRepository.findByKeyword(condition, pageable);
        assertThat(foundArticles.size()).isEqualTo(6);
        for (RentArticle foundArticle : foundArticles) {
            assertThat(foundArticle.isCompleted()).isFalse();
        }

        condition = new SearchConditionDto(false, null, null);
        foundArticles = rentArticleRepository.findByKeyword(condition, pageable);
        assertThat(foundArticles).isEqualTo(articles);
    }
}