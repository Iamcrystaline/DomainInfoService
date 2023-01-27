package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DomainInfoService {

    private DomainInfoRequester domainInfoRequester;

    private DomainPriceInfoRequester domainPriceInfoRequester;

    private DomainInfoCache cache;

    private DomainInfoParser domainInfoParser;

    private DomainPriceInfoParser domainPriceInfoParser;

    @Autowired
    public DomainInfoService(DomainInfoRequester domainInfoRequester,
                             DomainPriceInfoRequester domainPriceInfoRequester,
                             DomainInfoCache cache,
                             DomainInfoParser domainInfoParser,
                             DomainPriceInfoParser domainPriceInfoParser) {
        this.domainInfoRequester = domainInfoRequester;
        this.domainPriceInfoRequester = domainPriceInfoRequester;
        this.cache = cache;
        this.domainInfoParser = domainInfoParser;
        this.domainPriceInfoParser = domainPriceInfoParser;
    }

    public String getDomainInfo(String domain) {
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
