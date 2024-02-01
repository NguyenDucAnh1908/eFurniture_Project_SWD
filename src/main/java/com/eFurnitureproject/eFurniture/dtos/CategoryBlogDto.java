package com.eFurnitureproject.eFurniture.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBlogDto {

    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    @NotBlank(message = "Code is required")
    private String code;
}