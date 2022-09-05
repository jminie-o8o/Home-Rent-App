package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.Facility;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FacilityRepositoryTest {

    @Autowired
    private FacilityRepository facilityRepository;

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
    @DisplayName("findByName()을 호출하면 해당하는 이름의 Facility를 반환해야 한다.")
    public void findByNameTest() {
        Facility facility = new Facility(null, "냉장고");
        facilityRepository.save(facility);

        Facility refrigerator = facilityRepository.findByName("냉장고").orElse(null);
        assertThat(refrigerator).isNotNull();
        assertThat(refrigerator).isEqualTo(facility);

        Facility laundryMachine = facilityRepository.findByName("세탁기").orElse(null);
        assertThat(laundryMachine).isNull();
    }

}