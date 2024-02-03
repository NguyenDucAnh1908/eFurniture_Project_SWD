package com.eFurnitureproject.eFurniture.converter;


import com.eFurnitureproject.eFurniture.Responses.BlogResponse;
import com.eFurnitureproject.eFurniture.dtos.BlogDto;
import com.eFurnitureproject.eFurniture.models.Blog;
import org.springframework.stereotype.Component;

@Component
public class BlogConverter {
    public static BlogDto toDto(Blog entity) {
        BlogDto dto = new BlogDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setThumbnail(entity.getThumbnail());
        dto.setUserBlogId(entity.getUser().getId());
        dto.setTagBlogId(entity.getTagsBlog().getId());
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
        BlogResponse blogResponse = BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .thumbnail(blog.getThumbnail())
                .userBlogId(blog.getUser().getId())
                .tagBlogId(blog.getTagsBlog().getId())
                .categoryBlogId(blog.getCategoryBlog().getId())
                .active(blog.isActive())
                .thumbnail(blog.getThumbnail())
                .build();
        return blogResponse;
    }
}
