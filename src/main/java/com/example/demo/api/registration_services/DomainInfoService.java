package com.example.demo.api.registration_services;

import com.example.demo.api.Domain;
import com.example.demo.api.purchasing_services.DomainPurchaseService;
import com.example.demo.credentials.WhoIsXmlCredentials;
import com.example.demo.response_entities.DomainRegistrarInfo;
import com.example.demo.response_entities.WhoIsXmlResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomainInfoService {

    private final WhoIsXmlClient client;
    private final Cache<Domain, DomainRegistrarInfo> cache;
    private final Gson gson;
    private final DomainPurchaseService domainPurchaseService;
    private final WhoIsXmlCredentials credentials;

    public String getDomainInfo(Domain domain) {
        DomainRegistrarInfo cachedDomainRegistrarInfo = cache.getIfPresent(domain);
        if (cachedDomainRegistrarInfo != null) {
            return gson.toJson(cachedDomainRegistrarInfo);
        }
        WhoIsXmlResponse whoIsXmlResponse = client.getDomainInfo(
                credentials.getKey(),
                domain.getDomainFullName()
        );
        DomainRegistrarInfo domainRegistrarInfo = new DomainRegistrarInfo(
                whoIsXmlResponse.getWhoisRecord().getRegistrarName(),
                whoIsXmlResponse.getWhoisRecord().getRegistryData().getExpiresDateNormalized()
        );
        if (domainRegistrarInfo.getRegistrarName() == null) {
            return domainPurchaseService.getDomainPriceInfo(domain);
        }
        cache.put(domain, domainRegistrarInfo);
        return gson.toJson(domainRegistrarInfo);
    }
}
