package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.Facility;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        Facility refrigerator = facilityRepository.findByName("냉장고").orElseThrow(EntityNotFoundException::new);
        assertThat(refrigerator).isNotNull();

        Facility laundryMachine = facilityRepository.findByName("인덕션").orElseThrow(EntityNotFoundException::new);
        assertThat(laundryMachine).isNull();
    }
}