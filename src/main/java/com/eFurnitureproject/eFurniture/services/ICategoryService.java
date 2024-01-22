package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.CategoryDto;
import com.eFurnitureproject.eFurniture.models.Category;

public interface ICategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
}
