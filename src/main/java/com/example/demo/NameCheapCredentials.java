package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "namecheap.api")
public class NameCheapCredentials {

    private String key;
    private String api_user;
    private String client_ip;
}
