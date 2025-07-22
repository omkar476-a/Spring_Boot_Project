package com.example.project_1.service;

import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WRSService {

    private final WebClient webClient;

    public WRSService() {
        String username = "wcadmin";     // replace with actual username
        String password = "wcadmin";     // replace with actual password

        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        this.webClient = WebClient.builder()
                .baseUrl("http://ptpl312.pluraltechnology.com:80/Windchill/servlet/odata/v7")
                .defaultHeader("Authorization", "Basic " + encodedAuth)
                .build();
    }

    public String getPartsData() {
        return webClient.get()
                .uri("/ProdMgmt/Parts?$top=5&$count=false")  
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}