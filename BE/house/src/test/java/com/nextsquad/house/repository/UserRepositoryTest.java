package com.nextsquad.house.repository;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.repository.user.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;

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
    void setup() {
        user = userRepository.findById(1L).orElseThrow();
    }

    @Test
    @DisplayName("account id 로 유저를 찾는다")
    void findByAccountId() {
        //given
        String accountId = "street62";

        //when
        User found = userRepository.findByAccountId(accountId).orElseThrow();

        //then
        assertThat(found.getAccountId()).isEqualTo(user.getAccountId());
    }

    @Test
    @DisplayName("존재하지 않는 displayName으로 유저를 조회할때 false를 리턴한다")
    void existsUserByDisplayName(){
        //given
        String displayName = "없는유저";

        //when
        Boolean existsUserByDisplayName = userRepository.existsUserByDisplayName(displayName);

        //then
        assertThat(existsUserByDisplayName).isEqualTo(false);
    }
}
