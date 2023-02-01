package com.example.demo;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DomainInfoCache {

    private final Map<Domain, CacheableDomainInfo> cache;

    public DomainInfoCache(Map<Domain, CacheableDomainInfo> cache) {
        this.cache = cache;
    }

    public String checkCache(Domain domain) {
        CacheableDomainInfo domainInfo = cache.get(domain);
        if (domainInfo == null || domainInfo.cachedExpirationTime().isBefore(LocalDateTime.now())) {
            return "Not in cache";
        }
        return domainInfo.cachedData();
    }

    public void saveInCache(Domain key, String cachedData) {
        cache.put(key, new CacheableDomainInfo(cachedData, LocalDateTime.now().plusDays(1)));
    }
}
