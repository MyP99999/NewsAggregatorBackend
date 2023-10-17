package com.example.EvidenNewsAggregator.controllers;

import com.example.EvidenNewsAggregator.entities.tables.pojos.Articles;
import com.example.EvidenNewsAggregator.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ArticleController {

    @Autowired
    private final ArticleService articleService;

    @GetMapping
    public Iterable<Articles> findAll() {
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public Articles findById(@PathVariable Integer id) {
        return articleService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        int deletedRows = articleService.deleteById(id);

        if (deletedRows > 0) {
            return ResponseEntity.ok("Article with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody Articles article) {
        try {
            articleService.add(article);
            return ResponseEntity.ok("Article has been created.");
        } catch (IllegalArgumentException e) {
            // Handle the exception and return an error response
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateArticleFieldsById(@PathVariable Integer id, @RequestBody Articles updatedArticle) {
        int updatedRows = articleService.updateArticleFields(id, updatedArticle);

        if (updatedRows > 0) {
            return ResponseEntity.ok("Article with ID " + id + " has been updated.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
