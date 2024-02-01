package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.models.Blog;
import com.eFurnitureproject.eFurniture.repositories.BlogRepository;
import com.eFurnitureproject.eFurniture.services.IBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService implements IBlogService {
    private final BlogRepository blogRepository;

    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
}
