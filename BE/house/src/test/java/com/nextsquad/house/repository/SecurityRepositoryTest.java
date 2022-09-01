package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.Facility;
import com.nextsquad.house.domain.house.SecurityFacility;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("findByName()을 호출하면 해당하는 이름의Facility를 반환해야 한다.")
    public void findByNameTest() {
        SecurityFacility facility = new SecurityFacility(null, "도어락");
        securityRepository.save(facility);
        SecurityFacility doorLockFacility = securityRepository.findByName("도어락").orElse(null);
        assertThat(doorLockFacility).isNotNull();
        assertThat(doorLockFacility).isEqualTo(facility);

        SecurityFacility facilityNotInDb = securityRepository.findByName("인터폰").orElse(null);
        assertThat(facilityNotInDb).isNull();
    }

}