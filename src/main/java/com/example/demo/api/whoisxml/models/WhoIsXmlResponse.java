package com.example.demo.api.whoisxml.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WhoIsXmlResponse {

    private WhoisRecord WhoisRecord;

    @NoArgsConstructor
    @Getter
    public static class WhoisRecord {

        private String registrarName;
        private RegistryData registryData;

        @NoArgsConstructor
        @Getter
        public static class RegistryData {

            private String expiresDateNormalized;
        }
    }
}
