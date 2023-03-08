package com.example.demo.api.whoisxml;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@AllArgsConstructor
@Validated
@ConfigurationProperties(prefix = "whoisxmlapi.api")
public class WhoIsXmlCredentials {

    @NotBlank
    private String key;
    private String endpoint;
}
