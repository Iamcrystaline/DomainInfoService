package com.example.demo.api.namecheap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@AllArgsConstructor
@Validated
@ConfigurationProperties(prefix = "namecheap.api")
public class NameCheapCredentials {

    @NotBlank
    private String key;
    @NotBlank
    private String api_user;
    @Pattern(regexp = "^(([0-1]?\\d?\\d?|2[0-4]\\d|25[0-5])\\.){3}([0-1]?\\d?\\d?|2[0-4]\\d|25[0-5])$")
    private String client_ip;
    private String endpoint;
}
