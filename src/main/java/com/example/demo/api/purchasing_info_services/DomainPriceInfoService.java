package com.example.demo.api.purchasing_info_services;

import com.example.demo.api.Domain;
import com.example.demo.credentials.NameCheapCredentials;
import com.example.demo.response_entities.ApiResponse;
import com.example.demo.response_entities.ApiResponse.CommandResponse.UserGetPricingResult.ProductType.ProductCategory.Product.Price;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainPriceInfoService {

    private final NameCheapClient nameCheapClient;
    private final Cache<Domain, List<Price>> domainPriceInfoCache;
    private final Gson gson;
    private final NameCheapCredentials nameCheapCredentials;

    public String getDomainPriceInfo(Domain domain) {
        List<Price> cachedDomainPriceInfo = domainPriceInfoCache.getIfPresent(domain);
        if (cachedDomainPriceInfo != null) {
            return gson.toJson(cachedDomainPriceInfo);
        }
        ApiResponse apiResponse = nameCheapClient.getDomainPriceInfo(nameCheapCredentials.getApi_user(),
                nameCheapCredentials.getKey(),
                nameCheapCredentials.getClient_ip());
        List<Price> priceInfo = apiResponse.getCommandResponse()
                .getUserGetPricingResult()
                .getProductType()
                .getProductCategory()
                .getProduct()
                .getPrice();
        domainPriceInfoCache.put(domain, priceInfo);
        return gson.toJson(priceInfo);
    }
}
