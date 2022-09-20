package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.SecurityFacility;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SecurityRepositoryTest {

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
    @DisplayName("findByName()을 호출하면 해당하는 보안시설을 반환해야 한다.")
    public void findByNameTest() {
        SecurityFacility doorLockFacility = securityRepository.findByName("CCTV").orElse(null);
        assertThat(doorLockFacility).isNotNull();

        SecurityFacility facilityNotInDb = securityRepository.findByName("방범창").orElse(null);
        assertThat(facilityNotInDb).isNull();
    }

}