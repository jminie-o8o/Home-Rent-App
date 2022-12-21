package com.nextsquad.house.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void incrementTest() {
        assertThat(redisService.get("CountTest::" + 1L)).isNull();

        redisService.increment("CountTest::" + 1L);
        assertThat(redisService.get("CountTest::" + 1L)).isEqualTo("1");

        redisService.increment("CountTest::" + 1L);
        assertThat(redisService.get("CountTest::" + 1L)).isEqualTo("2");

        redisService.delete("CountTest::" + 1L);

        assertThat(redisService.get("CountTest::" + 1L)).isNull();
    }

    @Test
    public void decrementTest() {
        redisService.increment("CountTest::" + 1L);
        assertThat(redisService.get("CountTest::" + 1L)).isEqualTo("1");
        redisService.decrement("CountTest::" + 1L);
        assertThat(redisService.get("CountTest::" + 1L)).isEqualTo("0");
    }

}
