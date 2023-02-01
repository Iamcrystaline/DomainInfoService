package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

    @Test
    @DisplayName("Given existing domain vk.ru - When http get request - Then should return it's registrar and expiration time")
    public void testExistingDomain() {
        // Given
        String domainName = "vk.ru";
        String expectedAnswer = "{\"registrarName\":\"RU-CENTER-RU\",\"expiresDateNormalized\":\"2023-06-30 21:00:00 UTC\"}";

        // When
        String actual;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/v1/domainInfo?domainName=" + domainName))
                    .timeout(Duration.of(10, ChronoUnit.SECONDS))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            actual = response.body();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("API is not running");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong parameters");
        }

        // Then
        assertThat(actual).isEqualTo(expectedAnswer);
    }

    @Test
    @DisplayName("Given not existing domain abrafoo.com - When http get request - Then should return it's price")
    public void testNotExistingDomain() {
        // Given
        String domainName = "abrafoo.com";
        String expectedAnswer = "[{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":35.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":43.19," +
                "\"Duration\":1," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":35.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":32.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":79.18," +
                "\"Duration\":2," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":32.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":32.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":118.77," +
                "\"Duration\":3," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":32.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":32.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":158.36," +
                "\"Duration\":4," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":32.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":19.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":119.95," +
                "\"Duration\":5," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":19.99," +
                "\"PromotionPrice\":0}]";

        // When
        String actual;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/v1/domainInfo?domainName=" + domainName))
                    .timeout(Duration.of(10, ChronoUnit.SECONDS))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            actual = response.body();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("API is not running");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong parameters");
        }

        // Then
        assertThat(actual).isEqualTo(expectedAnswer);
    }
}
