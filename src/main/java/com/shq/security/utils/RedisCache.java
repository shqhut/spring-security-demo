package com.shq.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RedisCache {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> T getCacheObject(final String key){
        ValueOperations<String,T> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public<K,V> void setCacheHash(final String key, Map<K,V> value){
        BoundHashOperations<String,K,V> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.putAll(value);
    }

    public<K,V> V getCacheHash(final String key, K hashKey){
        HashOperations<String,K,V> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, hashKey);
    }

    /**
     * 删除单个key
     * @param key
     * @return
     */
    public boolean deleteObject(final String key){
        return redisTemplate.delete(key);
    }


}
