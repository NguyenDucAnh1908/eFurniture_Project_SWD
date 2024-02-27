package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.CategoryBlog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {
    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private String imageUrls;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long userBlogId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Long> categoryBlogIds;
    private List<Long> tagBlogIds;
    private boolean active;

}
