package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class DomainPriceInfoRequester {

    private final String domainPriceInfoEndpoint = "https://api.sandbox.namecheap.com/xml.response";

    @Value("${namecheap.api.key}")
    private String domainPriceInfoApiKey;

    @Value("${namecheap.api.api_user}")
    private String domainPriceInfoApiUser;

    @Value("${namecheap.api.client_ip}")
    private String domainPriceInfoClientIp;

    public String requestDomainPriceInfoApi() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(domainPriceInfoEndpoint +
                            "?ApiUser=" + domainPriceInfoApiUser +
                            "&ApiKey=" + domainPriceInfoApiKey +
                            "&UserName=" + domainPriceInfoApiUser +
                            "&ClientIP=" + domainPriceInfoClientIp +
                            "&Command=namecheap.users.getPricing" +
                            "&ProductType=SSLCERTIFICATE" +
                            "&ProductCategory=COMODO" +
                            "&ActionName=PURCHASE" +
                            "&ProductName=INSTANTSSL"))
                    .timeout(Duration.of(10, ChronoUnit.SECONDS))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Domain price info service is unavailable. Please, try again later");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong parameters");
        }
    }
}
