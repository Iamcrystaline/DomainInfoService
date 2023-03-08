package com.example.demo.api.whoisxml.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DomainRegistrarInfo {

    private String registrarName;
    private String expiresDateNormalized;
}
