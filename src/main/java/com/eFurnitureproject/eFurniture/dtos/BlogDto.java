package com.eFurnitureproject.eFurniture.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

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

    private Long tagBlogId;

    private Long categoryBlogId;
}