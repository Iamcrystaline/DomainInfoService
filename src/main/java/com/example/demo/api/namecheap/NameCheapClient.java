package com.example.demo.api.namecheap;

import com.example.demo.api.namecheap.models.ApiResponse;
import feign.Param;
import feign.RequestLine;

public interface NameCheapClient {

    @RequestLine("GET /xml.response" +
            "?ApiUser={apiUser}" +
            "&ApiKey={apiKey}" +
            "&UserName={apiUser}" +
            "&ClientIP={clientIP}" +
            "&Command=namecheap.users.getPricing" +
            "&ProductType=SSLCERTIFICATE" +
            "&ProductCategory=COMODO" +
            "&ActionName=PURCHASE" +
            "&ProductName=INSTANTSSL")
    ApiResponse getDomainPriceInfo(@Param("apiUser") String apiUser,
                                   @Param("apiKey") String apiKey,
                                   @Param("clientIP") String clientIP
    );
}
