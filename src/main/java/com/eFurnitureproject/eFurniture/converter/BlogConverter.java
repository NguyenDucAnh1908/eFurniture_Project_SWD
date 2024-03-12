package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.models.CategoryBlog;
import com.eFurnitureproject.eFurniture.Responses.BlogResponse;
import com.eFurnitureproject.eFurniture.dtos.BlogDto;
import com.eFurnitureproject.eFurniture.models.Blog;
import com.eFurnitureproject.eFurniture.models.TagsBlog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogConverter {
    public static BlogDto toDto(Blog entity) {
        BlogDto dto = new BlogDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setThumbnail(entity.getThumbnail());
        dto.setImageUrls(entity.getImageUrls());
        dto.setUserBlogId(entity.getUser().getId());
//        dto.setTagBlogId(entity.getTagsBlog().getId());
//        dto.setCategoryBlogId(entity.getCategoryBlog().getId());
        return dto;
    }

    public static Blog toEntity(BlogDto dto) {
        Blog entity = new Blog();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());
        // Trong thực tế, bạn sẽ cần thêm logic để lấy và thiết lập các đối tượng User, TagsBlog và CategoryBlog dựa trên các ID từ DTO.
        // entity.setUser(userRepository.findById(dto.getUserBlogId()).orElse(null));
        // entity.setTagsBlog(tagsBlogRepository.findById(dto.getTagBlogId()).orElse(null));
        // entity.setCategoryBlog(categoryBlogRepository.findById(dto.getCategoryBlogId()).orElse(null));
        return entity;
    }


    public static Blog toEntity(BlogDto dto, Blog entity) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());
//        entity.setDate_create(dto.getDateCreate());
        // Trong thực tế, bạn sẽ cần thêm logic để lấy và thiết lập các đối tượng User, TagsBlog và CategoryBlog dựa trên các ID từ DTO.
        // entity.setUser(userRepository.findById(dto.getUserBlogId()).orElse(null));
        // entity.setTagsBlog(tagsBlogRepository.findById(dto.getTagBlogId()).orElse(null));
        // entity.setCategoryBlog(categoryBlogRepository.findById(dto.getCategoryBlogId()).orElse(null));
        return entity;
    }

    public static BlogResponse toResponse(Blog blog) {
        List<Long> categoryIds = blog.getCategories().stream()
                .map(CategoryBlog::getId)
                .collect(Collectors.toList());

        List<Long> tagBlogIds = blog.getTagsBlog().stream()
                .map(TagsBlog::getId)
                .collect(Collectors.toList());

        List<String> categoryNames = blog.getCategories().stream()
                .map(CategoryBlog::getName)
                .collect(Collectors.toList());

        List<String> tagsBlogName = blog.getTagsBlog().stream()
                .map(TagsBlog::getTagName)
                .collect(Collectors.toList());



        BlogResponse blogResponse = BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .thumbnail(blog.getThumbnail())
                .imageUrls(blog.getImageUrls())
                .userBlogId(blog.getUser().getId())
                .tagBlogIds(tagBlogIds)
                .categoryBlogIds(categoryIds)
                .categoryNames(categoryNames)
                .tagsBlogName(tagsBlogName)
                .userFullName(blog.getUser().getFullName())
                .active(blog.isActive())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
        return blogResponse;
    }

}
