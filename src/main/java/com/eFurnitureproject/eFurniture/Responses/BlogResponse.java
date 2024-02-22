package com.eFurnitureproject.eFurniture.Responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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
    private Long userBlogId; // Assuming you want to use userBlogId instead of User object directly

    private Long tagBlogId; // Assuming you want to use tagBlogId instead of TagsBlog object directly

    private Long categoryBlogId; // Assuming you want to use categoryBlogId instead of CategoryBlog object directly
    private boolean active;

}
