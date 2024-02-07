package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.BlogImagesDto;
import com.eFurnitureproject.eFurniture.models.BlogImages;

public class BlogImagesConverter {
    public static BlogImagesDto toDto(BlogImages entity) {
        BlogImagesDto dto = new BlogImagesDto();
        dto.setImageUrl(entity.getImageUrl());
        dto.setBlogId(entity.getBlog().getId());
        return dto;
    }

    public static BlogImages toEntity(BlogImagesDto dto) {
        BlogImages entity = new BlogImages();
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

    public static BlogImages toEntity(BlogImagesDto dto, BlogImages entity) {
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }
}
