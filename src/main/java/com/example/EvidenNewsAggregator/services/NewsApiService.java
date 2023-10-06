package com.example.EvidenNewsAggregator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NewsApiService {

    @Value("${newsapi.url}")
    private String apiUrl;

    @Value("${newsapi.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public NewsApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getNews() {
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String finalUrl = apiUrl.replace("{date}", yesterday).replace("{apiKey}", apiKey);
        System.out.println(finalUrl);
        return this.restTemplate.getForObject(finalUrl, String.class);
    }

}
