package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.CategoryConverter;
import com.eFurnitureproject.eFurniture.dtos.CategoryDto;
import com.eFurnitureproject.eFurniture.models.Category;
import com.eFurnitureproject.eFurniture.repositories.CategoryRepository;
import com.eFurnitureproject.eFurniture.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryConverter.toEntity(categoryDto);
        category = categoryRepository.save(category);
        return CategoryConverter.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = new Category();
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(existingCategory.isPresent()){
            Category oldCategory = existingCategory.get();
            category = CategoryConverter.toEntity(categoryDto, oldCategory);
        }
        category = categoryRepository.save(category);
        return CategoryConverter.toDto(category);
    }
}
