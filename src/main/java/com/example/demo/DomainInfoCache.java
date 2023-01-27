package com.example.demo;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DomainInfoCache {

    private Map<String, CacheableDomainInfo> cache;

    public DomainInfoCache(Map<String, CacheableDomainInfo> cache) {
        this.cache = cache;
    }

    public String checkCache(String domain) {
        CacheableDomainInfo domainInfo = cache.get(domain);
        if (domainInfo == null || domainInfo.cachedExpirationTime().isBefore(LocalDateTime.now())) {
            return "Not in cache";
        }
        return domainInfo.cachedData();
    }

    public void saveInCache(String key, String cachedData) {
        cache.put(key, new CacheableDomainInfo(cachedData, LocalDateTime.now().plusDays(1)));
    }
}
