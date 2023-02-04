package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "whoisxmlapi.api")
public class WhoIsXmlCredentials {

    private String key;
}
