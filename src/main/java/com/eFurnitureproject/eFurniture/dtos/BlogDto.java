package com.eFurnitureproject.eFurniture.dtos;

import com.eFurnitureproject.eFurniture.models.CategoryBlog;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDto {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 0, max = 5000, message = "Title must be between 0 and 5000 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 0, max = 65535, message = "Content must be between 0 and 1000000 characters")
    private String content;

    private String thumbnail;
    private String imageUrls;

    private boolean active;

    private Long userBlogId;

    private List<Long> categoryBlogIds;

    private List<Long> tagBlogIds;
    public List<Long> getCategoryBlogIds() {
        return categoryBlogIds;
    }

    public void setCategoryBlogIds(List<Long> categoryBlogIds) {
        this.categoryBlogIds = categoryBlogIds;
    }

    public List<Long> getTagBlogIds() {
        return tagBlogIds;
    }

    public void setTagBlogIds(List<Long> tagBlogIds) {
        this.tagBlogIds = tagBlogIds;
    }
}