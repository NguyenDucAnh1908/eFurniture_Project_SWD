package com.eFurnitureproject.eFurniture.Responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBlogResponse {

    private Long id;
    private String name;
    private String code;
}
