package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Bean
    public Map<Domain, CacheableDomainInfo> getCache() {
        return new HashMap<>();
    }
}
