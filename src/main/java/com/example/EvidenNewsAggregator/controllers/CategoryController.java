package com.example.EvidenNewsAggregator.controllers;

import com.example.EvidenNewsAggregator.entities.tables.pojos.Categories;
import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.services.CategoryService;
import com.example.EvidenNewsAggregator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping
    public Iterable<Categories> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Categories findById(@PathVariable Integer id){
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Categories category) {
        categoryService.add(category);

        return ResponseEntity.ok("Category has been created.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        int deletedRows = categoryService.deleteById(id);

        if (deletedRows > 0) {
            return ResponseEntity.ok("Category with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}