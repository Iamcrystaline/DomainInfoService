package com.example.demo;

import com.example.demo.api.Domain;
import com.example.demo.api.registration_services.WhoIsXmlClient;
import com.example.demo.api.purchasing_services.NameCheapClient;
import com.example.demo.credentials.NameCheapCredentials;
import com.example.demo.credentials.WhoIsXmlCredentials;
import com.example.demo.response_entities.DomainRegistrarInfo;
import com.example.demo.response_entities.ApiResponse.CommandResponse.UserGetPricingResult.ProductType.ProductCategory.Product.Price;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.gson.Gson;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class Config {

    private final WhoIsXmlCredentials whoIsXmlCredentials;
    private final NameCheapCredentials nameCheapCredentials;

    @Bean
    public Cache<Domain, DomainRegistrarInfo> getDomainInfoCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .maximumSize(100)
                .build();
    }

    @Bean
    public Cache<Domain, List<Price>> getDomainPriceInfoCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .maximumSize(100)
                .build();
    }

    @Bean
    public Gson getGson() {
        return new Gson();
    }

    @Bean
    public WhoIsXmlClient getDomainInfoRequester() {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .target(WhoIsXmlClient.class, whoIsXmlCredentials.getEndpoint());
    }

    @Bean
    NameCheapClient getDomainPriceInfoRequester() {
        return Feign.builder()
                .decoder(new JacksonDecoder(new XmlMapper()))
                .target(NameCheapClient.class, nameCheapCredentials.getEndpoint());
    }
}
