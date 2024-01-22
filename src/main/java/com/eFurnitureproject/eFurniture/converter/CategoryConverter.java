package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.CategoryDto;
import com.eFurnitureproject.eFurniture.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public static CategoryDto toDto(Category entity){
        CategoryDto dto = new CategoryDto();
        dto.setName(entity.getName());
        return dto;
    }

    public static Category toEntity(CategoryDto dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        return entity;
    }

    public static Category toEntity(CategoryDto dto, Category entity){
        entity.setName(dto.getName());
        return entity;
    }
}
