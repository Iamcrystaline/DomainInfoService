package com.example.demo.api.registration_info_services;

import com.example.demo.api.Domain;
import com.example.demo.api.purchasing_info_services.DomainPriceInfoService;
import com.example.demo.credentials.WhoIsXmlCredentials;
import com.example.demo.response_entities.DomainInfo;
import com.example.demo.response_entities.WhoIsXmlResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomainInfoService {

    private final WhoIsXmlClient whoIsXmlClient;
    private final Cache<Domain, DomainInfo> domainInfoCache;
    private final Gson gson;
    private final DomainPriceInfoService domainPriceInfoService;
    private final WhoIsXmlCredentials whoIsXmlCredentials;

    public String getDomainInfo(Domain domain) {
        DomainInfo cachedDomainInfo = domainInfoCache.getIfPresent(domain);
        if (cachedDomainInfo != null) {
            return gson.toJson(cachedDomainInfo);
        }
        WhoIsXmlResponse whoIsXmlResponse = whoIsXmlClient.getDomainInfo(whoIsXmlCredentials.getKey(),
                domain.getDomainFullName());
        DomainInfo domainInfo = new DomainInfo(whoIsXmlResponse.getWhoisRecord().getRegistrarName(),
                whoIsXmlResponse.getWhoisRecord().getRegistryData().getExpiresDateNormalized());
        if (domainInfo.getRegistrarName() == null) {
            return domainPriceInfoService.getDomainPriceInfo(domain);
        }
        domainInfoCache.put(domain, domainInfo);
        return gson.toJson(domainInfo);
    }
}
