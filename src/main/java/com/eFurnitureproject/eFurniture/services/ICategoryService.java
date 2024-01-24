package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();
}
