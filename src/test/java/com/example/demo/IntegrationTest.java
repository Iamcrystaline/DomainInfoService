package com.example.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

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
        whoIsXmlMockServer.stubFor(WireMock
                .get(urlEqualTo("/whoisserver/WhoisService?apiKey=aaa&domainName=vk.ru&outputFormat=JSON"))
                .willReturn(aResponse()
                        .withBodyFile("vkInfo.json")
                        .withStatus(200)
                )
        );
        String domainName = "vk.ru";

        // When
        // Then
        io.restassured.RestAssured.get("/api/v1/domainInfo?domainName=" + domainName)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("expected_answers/domain_registrar_info.json"));
    }

    @Test
    @DisplayName("Given not existing domain abrafoo.com - When http get request - Then should return it's price")
    public void testNotExistingDomain() {
        // Given
        whoIsXmlMockServer.stubFor(WireMock
                .get(urlEqualTo("/whoisserver/WhoisService?apiKey=aaa&domainName=abrafoo.com&outputFormat=JSON"))
                .willReturn(aResponse()
                        .withBodyFile("abrafooInfo.json")
                        .withStatus(200)
                )
        );
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

        // When
        // Then
        io.restassured.RestAssured.get("/api/v1/domainInfo?domainName=" + domainName)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("expected_answers/domain_purchase_info.json"));
    }
}
