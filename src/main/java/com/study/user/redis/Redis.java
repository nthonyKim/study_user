package com.study.user.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class Redis {
    @Value("${spring.profiles.active}")
    private String profile;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    public void putHash(String key, String hashKey, String value) {
        hashOperations.put(profile + ":" + key, hashKey, value);
    }

    public Map<String, String> getEntries(String key) {
        return hashOperations.entries(profile + ":" + key);
    }

}
