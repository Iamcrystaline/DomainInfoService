package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class IntegrationTest {

    private static WireMockServer whoIsXmlMockServer;
    private static WireMockServer nameCheapMockServer;

    @BeforeAll
    static void beforeAll() {
        whoIsXmlMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(8081));
        whoIsXmlMockServer.start();
        nameCheapMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(8082));
        nameCheapMockServer.start();
    }

    @AfterAll
    static void afterAll() {
        whoIsXmlMockServer.stop();
        nameCheapMockServer.stop();
    }

    @Test
    @DisplayName("Given existing domain vk.ru - When http get request - Then should return it's registrar and expiration time")
    public void testExistingDomain() {
        // Given
        whoIsXmlMockServer.stubFor(WireMock.get(urlEqualTo("/whoisserver/WhoisService?apiKey=aaa&domainName=vk.ru&outputFormat=JSON"))
                .willReturn(aResponse().withBodyFile("vkInfo.json")
                        .withStatus(200)));
        String domainName = "vk.ru";
        String expectedAnswer = "";
        try {
            expectedAnswer = new String(Files.readAllBytes(Paths.get("src/test/resources/expected_answers/vk_response.json")));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Expected answer file not found");
        }

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
        ObjectMapper mapper = new ObjectMapper();
        try {
            assertThat(mapper.readTree(actual)).isEqualTo(mapper.readTree(expectedAnswer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Wrong response format");
        }
    }

    @Test
    @DisplayName("Given not existing domain abrafoo.com - When http get request - Then should return it's price")
    public void testNotExistingDomain() {
        // Given
        whoIsXmlMockServer.stubFor(WireMock.get(urlEqualTo("/whoisserver/WhoisService?apiKey=aaa&domainName=abrafoo.com&outputFormat=JSON"))
                .willReturn(aResponse().withBodyFile("abrafooInfo.json")
                        .withStatus(200)));
        nameCheapMockServer.stubFor(WireMock.get(urlEqualTo("/xml.response" +
                        "?ApiUser=foobar" +
                        "&ApiKey=bbb" +
                        "&UserName=foobar" +
                        "&ClientIP=192.168.1.1" +
                        "&Command=namecheap.users.getPricing" +
                        "&ProductType=SSLCERTIFICATE" +
                        "&ProductCategory=COMODO" +
                        "&ActionName=PURCHASE" +
                        "&ProductName=INSTANTSSL"
                ))
                .willReturn(aResponse().withBodyFile("priceInfo.xml")
                        .withStatus(200)));
        String domainName = "abrafoo.com";
        String expectedAnswer = "";
        try {
            expectedAnswer = new String(Files.readAllBytes(Paths.get("src/test/resources/expected_answers/abrafoo_response.json")));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Expected answer file not found");
        }

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
        ObjectMapper mapper = new ObjectMapper();
        try {
            assertThat(mapper.readTree(actual)).isEqualTo(mapper.readTree(expectedAnswer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Wrong response format");
        }
    }
}
