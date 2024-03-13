package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.models.CategoryBlog;
import com.eFurnitureproject.eFurniture.repositories.CategoryBlogRepository;
import com.eFurnitureproject.eFurniture.services.ICategoriesBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesBlogService implements ICategoriesBlogService {
   private final CategoryBlogRepository categoryBlogRepository;

   @CrossOrigin
    @Override
    public List<CategoryBlog> getAllCategoriesBlog() {
        return categoryBlogRepository.findAll();
    }

    @Override
    public CategoryBlog getCategoryById(Long id) {
        return categoryBlogRepository.findById(id).orElse(null);
    }

    @Override
    public CategoryBlog saveCategory(CategoryBlog categoryBlog) {
        return categoryBlogRepository.save(categoryBlog);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryBlogRepository.deleteById(id);
    }
}
