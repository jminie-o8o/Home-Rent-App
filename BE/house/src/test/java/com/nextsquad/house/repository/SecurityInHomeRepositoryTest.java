package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleSecurityFacility;
import com.nextsquad.house.domain.house.SecurityFacility;
import com.nextsquad.house.exception.ArticleNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SecurityInHomeRepositoryTest {

    @Autowired
    private SecurityInHomeRepository securityInHomeRepository;
    @Autowired
    private RentArticleRepository rentArticleRepository;
    @Autowired
    private SecurityRepository securityRepository;
    private RentArticle articleOne;
    private RentArticle articleTwo;

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
    public void articleSetup() {
        articleOne = rentArticleRepository.findById(1L).orElseThrow(ArticleNotFoundException::new);
        articleTwo = rentArticleRepository.findById(2L).orElseThrow(ArticleNotFoundException::new);
    }

    @Test
    @Order(1)
    @DisplayName("securityInHome.deleteAll()을 호출하면 해당 게시글의 보안시설이 모두 삭제되어야 한다.")
    public void deleteAllByRentArticleTest() {
        SecurityFacility securityOne = securityRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        SecurityFacility securityTwo = securityRepository.findById(2L).orElseThrow(EntityNotFoundException::new);

        RentArticleSecurityFacility securityInHomeOne = new RentArticleSecurityFacility(articleOne, securityOne);
        RentArticleSecurityFacility securityInHomeTwo = new RentArticleSecurityFacility(articleOne, securityTwo);
        RentArticleSecurityFacility securityInHomeThree = new RentArticleSecurityFacility(articleTwo, securityOne);

        securityInHomeRepository.save(securityInHomeOne);
        securityInHomeRepository.save(securityInHomeTwo);
        securityInHomeRepository.save(securityInHomeThree);

        List<RentArticleSecurityFacility> before = securityInHomeRepository.findAll();

        securityInHomeRepository.deleteAllByRentArticle(articleOne);

        List<RentArticleSecurityFacility> after = securityInHomeRepository.findAll();

        assertThat(before.size()).isEqualTo(3);
        assertThat(after.size()).isEqualTo(1);
        assertThat(after.get(0)).isEqualTo(securityInHomeThree);
    }

}

