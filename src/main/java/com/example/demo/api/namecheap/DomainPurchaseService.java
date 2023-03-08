package com.example.demo.api.namecheap;

import com.example.demo.api.Domain;
import com.example.demo.api.namecheap.models.ApiResponse;
import com.example.demo.api.namecheap.models.ApiResponse.CommandResponse.UserGetPricingResult.ProductType.ProductCategory.Product.Price;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainPurchaseService {

    private final NameCheapClient client;
    private final Cache<Domain, List<Price>> cache;
    private final Gson gson;
    private final NameCheapCredentials credentials;

    public String getDomainPriceInfo(Domain domain) {
        List<Price> cachedDomainPriceInfo = cache.getIfPresent(domain);
        if (cachedDomainPriceInfo != null) {
            return gson.toJson(cachedDomainPriceInfo);
        }
        ApiResponse apiResponse = client.getDomainPriceInfo(
                credentials.getApi_user(),
                credentials.getKey(),
                credentials.getClient_ip()
        );
        List<Price> priceInfo = apiResponse.getCommandResponse()
                .getUserGetPricingResult()
                .getProductType()
                .getProductCategory()
                .getProduct()
                .getPrice();
        cache.put(domain, priceInfo);
        return gson.toJson(priceInfo);
    }
}
