package com.example.EvidenNewsAggregator.controllers;

import com.example.EvidenNewsAggregator.services.NewsApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/api/news")
public class NewsApiController {

    private final NewsApiService newsApiService;

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }


    @GetMapping
    public ResponseEntity<String> fetchNews() {
        try {
            String news = newsApiService.getNews();
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (RestClientException e) {
            return new ResponseEntity<>("Failed to fetch news", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
