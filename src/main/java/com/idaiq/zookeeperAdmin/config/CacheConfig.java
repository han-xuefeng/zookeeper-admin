package com.idaiq.zookeeperAdmin.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.idaiq.zookeeperAdmin.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean(name = "tokenManager")
    public Cache<String, User> tokenManager(){
        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }
}
