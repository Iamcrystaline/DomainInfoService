package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DomainInfoParserTest {

    private DomainInfoParser underTest = new DomainInfoParser();

    @Test
    @DisplayName("Given JSON without error - When parseDomainInfo - Then should return registrarName and expirationTime")
    void parseDomainInfoWithoutErrors() {
        // Given
        String JSON = """
                {
                  "WhoisRecord": {
                    "registrarName": "RU-CENTER-RU",
                    "registryData": {
                      "expiresDateNormalized": "2023-06-30 21:00:00 UTC"
                    }
                  }
                }""";
        String expected = "{\"registrarName\":\"RU-CENTER-RU\",\"expiresDateNormalized\":\"2023-06-30 21:00:00 UTC\"}";

        // When
        String actual = underTest.parseDomainInfo(JSON);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Given JSON with error - When parseDomainInfo - Then should return 'Not found'")
    void parseDomainInfoWithErrors() {
        // Given
        String JSON = """
                {
                  "dataError": "MISSING_WHOIS_DATA"
                }""";
        String expected = "Not found";

        // When
        String actual = underTest.parseDomainInfo(JSON);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}