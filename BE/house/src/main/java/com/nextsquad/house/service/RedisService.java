package com.nextsquad.house.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public String get(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }
    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void save(String key, String value, int minutes) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(minutes));
    }

    public void increment(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.setIfAbsent(key, String.valueOf(0));

        values.increment(key);
    }

    public void decrement(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.decrement(key);
    }

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }
}
