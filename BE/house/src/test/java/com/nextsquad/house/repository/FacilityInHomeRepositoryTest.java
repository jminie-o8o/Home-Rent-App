package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.*;
import com.nextsquad.house.exception.ArticleNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FacilityInHomeRepositoryTest {

    @Autowired
    private FacilityInHomeRepository facilityInHomeRepository;
    @Autowired
    private RentArticleRepository rentArticleRepository;
    @Autowired
    private FacilityRepository facilityRepository;
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
        Facility facilityOne = facilityRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        Facility facilityTwo = facilityRepository.findById(2L).orElseThrow(EntityNotFoundException::new);

        RentArticleFacility facilityInHomeOne = new RentArticleFacility(articleOne, facilityOne);
        RentArticleFacility facilityInHomeTwo = new RentArticleFacility(articleOne, facilityTwo);
        RentArticleFacility facilityInHomeThree = new RentArticleFacility(articleTwo, facilityOne);

        facilityInHomeRepository.save(facilityInHomeOne);
        facilityInHomeRepository.save(facilityInHomeTwo);
        facilityInHomeRepository.save(facilityInHomeThree);

        List<RentArticleFacility> before = facilityInHomeRepository.findAll();

        facilityInHomeRepository.deleteAllByRentArticle(articleOne);

        List<RentArticleFacility> after = facilityInHomeRepository.findAll();

        assertThat(before.size()).isEqualTo(3);
        assertThat(after.size()).isEqualTo(1);
        assertThat(after.get(0)).isEqualTo(facilityInHomeThree);
    }

}

