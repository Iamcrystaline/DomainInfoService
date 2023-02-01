package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomainInfoService {

    private final DomainInfoRequester domainInfoRequester;
    private final DomainPriceInfoRequester domainPriceInfoRequester;
    private final DomainInfoCache cache;
    private final DomainInfoParser domainInfoParser;
    private final DomainPriceInfoParser domainPriceInfoParser;

    public String getDomainInfo(Domain domain) {
        String cacheSearchResult = cache.checkCache(domain);
        if (!cacheSearchResult.equals("Not in cache")) {
            return cacheSearchResult;
        }
        String domainInfo = domainInfoRequester.requestDomainInfoApi(domain);
        String parsedDomainInfo = domainInfoParser.parseDomainInfo(domainInfo);
        if (parsedDomainInfo.equals("Not found")) {
            String domainPriceInfo = domainPriceInfoRequester.requestDomainPriceInfoApi();
            String parsedDomainPriceInfo = domainPriceInfoParser.parseDomainPriceInfo(domainPriceInfo);
            cache.saveInCache(domain, parsedDomainPriceInfo);
            return parsedDomainPriceInfo;
        }
        cache.saveInCache(domain, parsedDomainInfo);
        return parsedDomainInfo;
    }
}
