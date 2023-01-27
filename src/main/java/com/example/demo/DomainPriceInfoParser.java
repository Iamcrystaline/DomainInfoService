package com.example.demo;

import org.json.JSONArray;
import org.json.XML;
import org.springframework.stereotype.Service;

@Service
public class DomainPriceInfoParser {

    public String parseDomainPriceInfo(String domainPriceInfo) {
        JSONArray prices = XML.toJSONObject(domainPriceInfo)
                .getJSONObject("ApiResponse")
                .getJSONObject("CommandResponse")
                .getJSONObject("UserGetPricingResult")
                .getJSONObject("ProductType")
                .getJSONObject("ProductCategory")
                .getJSONObject("Product")
                .getJSONArray("Price");
        return prices.toString();
    }
}
