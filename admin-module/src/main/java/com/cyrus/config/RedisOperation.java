package com.cyrus.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public class RedisOperation<T extends Serializable> {

    private final RedisTemplate<String, Object> redisTemplate;


    public void saveRedisValue(String key, T data) {
        redisTemplate.opsForValue().set(key, data, Duration.ofDays(1));
    }

    public void saveRedisList(String key, List<?> data) {
        redisTemplate.opsForValue().set(key, data, Duration.ofDays(1));
    }

    public List<?> getRedisList(String key) {
        return (List<?>) redisTemplate.opsForValue().get(key);
    }

    public T getRedisValue(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public T deleteRedisValue(String key) {
        return (T) redisTemplate.opsForValue().getAndDelete(key);
    }
}
