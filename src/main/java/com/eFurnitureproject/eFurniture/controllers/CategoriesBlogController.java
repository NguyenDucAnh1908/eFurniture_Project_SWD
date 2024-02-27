package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.models.CategoryBlog;
import com.eFurnitureproject.eFurniture.services.impl.CategoriesBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories-blog")
@RequiredArgsConstructor
public class CategoriesBlogController {
    private final CategoriesBlogService categoriesBlogService;

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<CategoryBlog> getCategoryById(@PathVariable Long id){
        CategoryBlog category = categoriesBlogService.getCategoryById(id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
