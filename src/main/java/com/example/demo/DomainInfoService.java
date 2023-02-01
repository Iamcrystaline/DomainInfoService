package com.example.demo;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomainInfoService {

    private final DomainInfoRequester domainInfoRequester;
    private final DomainPriceInfoRequester domainPriceInfoRequester;
    private final DomainInfoParser domainInfoParser;
    private final DomainPriceInfoParser domainPriceInfoParser;
    private final Cache<Domain, String> cache;

    public String getDomainInfo(Domain domain) {
        String cacheSearchResult = cache.getIfPresent(domain);
        if (cacheSearchResult != null) {
            return cacheSearchResult;
        }
        String domainInfo = domainInfoRequester.requestDomainInfoApi(domain);
        String parsedDomainInfo = domainInfoParser.parseDomainInfo(domainInfo);
        if (parsedDomainInfo.equals("Not found")) {
            String domainPriceInfo = domainPriceInfoRequester.requestDomainPriceInfoApi();
            String parsedDomainPriceInfo = domainPriceInfoParser.parseDomainPriceInfo(domainPriceInfo);
            cache.put(domain, parsedDomainPriceInfo);
            return parsedDomainPriceInfo;
        }
        cache.put(domain, parsedDomainInfo);
        return parsedDomainInfo;
    }
}
