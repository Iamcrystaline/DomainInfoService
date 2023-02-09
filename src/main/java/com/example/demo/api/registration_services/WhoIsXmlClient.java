package com.example.demo.api.registration_services;

import com.example.demo.response_entities.WhoIsXmlResponse;
import feign.Param;
import feign.RequestLine;

public interface WhoIsXmlClient {

    @RequestLine("GET /whoisserver/WhoisService?apiKey={apiKey}&domainName={domainName}&outputFormat=JSON")
    WhoIsXmlResponse getDomainInfo(@Param("apiKey") String apiKey, @Param("domainName") String domainName);
}
