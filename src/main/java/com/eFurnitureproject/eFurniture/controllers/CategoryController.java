package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.dtos.CategoryDto;
import com.eFurnitureproject.eFurniture.services.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDto categoryDTO,
            BindingResult result
    ){
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryDto categoryDTO,
            BindingResult result
    ){
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }
}
