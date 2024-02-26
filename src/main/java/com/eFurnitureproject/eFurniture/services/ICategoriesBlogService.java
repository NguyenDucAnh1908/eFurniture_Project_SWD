package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.models.CategoryBlog;

import java.util.List;

public interface ICategoriesBlogService {

    List<CategoryBlog> getAllCategoriesBlog();
    CategoryBlog getCategoryById(Long id);
    CategoryBlog saveCategory(CategoryBlog categoryBlog);
    void deleteCategory(Long id);

}
