package com.nextsquad.house.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public String getRefreshToken(String accountId){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(accountId);
    }
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void save(String key, String value, int time) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(time));
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }
}
