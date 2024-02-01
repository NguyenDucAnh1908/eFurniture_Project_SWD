package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.BlogResponse;
import com.eFurnitureproject.eFurniture.converter.BlogConverter;
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


    @Override
    public Page<BlogResponse> getAllBlogs(String keyword, Pageable pageable,
                                          Long userBlogId, Long tagsBlogId) {
        Page<Blog> blogs = blogRepository.searchBlogs(
                keyword, pageable, userBlogId, tagsBlogId);

        return blogs.map(BlogConverter::toResponse);
    }
}
