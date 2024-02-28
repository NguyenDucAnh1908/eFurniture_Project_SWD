package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.BlogResponse;
import com.eFurnitureproject.eFurniture.dtos.BlogDto;
import com.eFurnitureproject.eFurniture.models.Blog;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IBlogService {
     Page<BlogResponse> getAllBlogs(String keyword, Pageable pageable,
                                          Long userBlogId, Long tagsBlogId);
     Blog createBlog(BlogDto blogDto) throws EntityNotFoundException;
     Blog updateBlog(Long blogId, BlogDto updatedBlogDto) throws EntityNotFoundException;
     BlogResponse DeactivateBlog(Long blogId) throws EntityNotFoundException;
     String uploadThumbnailToCloudinary(Long blogId, MultipartFile image) throws IOException;
}
