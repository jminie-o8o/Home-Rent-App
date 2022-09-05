package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleSecurityFacility;
import com.nextsquad.house.domain.house.SecurityFacility;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SecurityInHomeRepositoryTest {

    @Autowired
    private SecurityInHomeRepository securityInHomeRepository;

    @Autowired
    private RentArticleRepository rentArticleRepository;

    @Autowired
    private SecurityRepository securityRepository;

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
    @DisplayName("deleteAllByRentArticle() 이 호출되면 특정 RentArticle에 해당하는 보안시설이 전부 삭제되어야 한다.")
    public void deleteAllByRentArticleTest() {
        RentArticle articleOne = new RentArticle();
        RentArticle articleTwo = new RentArticle();
        rentArticleRepository.save(articleOne);
        rentArticleRepository.save(articleTwo);

        SecurityFacility securityOne = new SecurityFacility();
        SecurityFacility securityTwo = new SecurityFacility();
        securityRepository.save(securityOne);
        securityRepository.save(securityTwo);

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