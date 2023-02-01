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
public class DomainInfoRequester {

    private final String domainInfoEndpoint = "https://www.whoisxmlapi.com/whoisserver/WhoisService";

    @Value("${whoisxmlapi.api.key}")
    private String domainInfoApiKey;

    public String requestDomainInfoApi(Domain domain) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(domainInfoEndpoint + "?apiKey=" + domainInfoApiKey +
                            "&domainName=" + domain.getDomainFullName() +
                            "&outputFormat=JSON"))
                    .timeout(Duration.of(10, ChronoUnit.SECONDS))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Domain info service is unavailable. Please, try again later");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong parameters");
        }
    }
}
