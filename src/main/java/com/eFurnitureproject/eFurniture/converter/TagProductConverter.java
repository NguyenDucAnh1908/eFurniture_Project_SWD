package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.TagProductDto;
import com.eFurnitureproject.eFurniture.models.TagsProduct;
import org.springframework.stereotype.Component;

@Component
public class TagProductConverter {
    public static TagProductDto toDto(TagsProduct entity) {
        TagProductDto dto = new TagProductDto();
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        return dto;
    }

    public static TagsProduct toEntity(TagProductDto dto) {
        TagsProduct entity = new TagsProduct();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }

    public static TagsProduct toEntity(TagProductDto dto, TagsProduct entity) {
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }
}
