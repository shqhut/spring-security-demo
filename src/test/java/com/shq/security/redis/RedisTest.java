package com.shq.security.redis;

import com.shq.security.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisCache redisCache;

    @Test
    public void testRedis(){
        redisCache.setCacheObject("shq","redis测试");
    }

    @Test
    public void testRedisGet(){
        String value = redisCache.getCacheObject("shq");
        System.out.println(value);
    }

    @Test
    public void testRedisHashSet(){
        Map<String, TestEntity> map = new HashMap<>();
        TestEntity testEntity = TestEntity.builder()
                .name("宋恒强")
                .age(25).build();
        map.put("shq",testEntity);
        redisCache.setCacheHash("hashTest",map);
    }

    @Test
    public void testRedisHashGet(){
        TestEntity cacheHash = redisCache.getCacheHash("hashTest", "shq");
        System.out.println(cacheHash);
    }


}
