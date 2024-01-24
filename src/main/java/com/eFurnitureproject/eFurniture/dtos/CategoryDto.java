package com.eFurnitureproject.eFurniture.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;
    private String code;
}
