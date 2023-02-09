package com.example.demo.response_entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DomainRegistrarInfo {

    private String registrarName;
    private String expiresDateNormalized;
}
