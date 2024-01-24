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
        String generatedCode = generateCodeFromName(categoryDto.getName());
        Category category = CategoryConverter.toEntity(categoryDto);
        category.setCode(generatedCode);
        category = categoryRepository.save(category);
        return CategoryConverter.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        String generatedCode = generateCodeFromName(categoryDto.getName());
        Category category = new Category();
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(existingCategory.isPresent()){
            Category oldCategory = existingCategory.get();
            category = CategoryConverter.toEntity(categoryDto, oldCategory);
            category.setCode(generatedCode);
        }
        category = categoryRepository.save(category);
        return CategoryConverter.toDto(category);
    }
    private String generateCodeFromName(String name) {
        // Thực hiện logic tạo mã của bạn ở đây
        // Đơn giản, bạn có thể sử dụng một logic cơ bản như loại bỏ khoảng trắng và chuyển đổi thành chữ in hoa
        return name.replaceAll("\\s", "-").toLowerCase();
    }
}
