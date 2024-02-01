package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.BlogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBlogService {
     Page<BlogResponse> getAllBlogs(String keyword, Pageable pageable,
                                          Long userBlogId, Long tagsBlogId);
}
