package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.BrandDto;
import com.eFurnitureproject.eFurniture.models.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter {
    public static BrandDto toDto(Brand entity) {
        BrandDto dto = new BrandDto();
        dto.setName(entity.getName());
        dto.setLogo(entity.getLogo());
        dto.setWebsiteUrl(entity.getWebsiteUrl());
        dto.setCode(entity.getCode());
        return dto;
    }

    public static Brand toEntity(BrandDto dto) {
        Brand entity = new Brand();
        entity.setName(dto.getName());
        entity.setLogo(dto.getLogo());
        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setCode(dto.getCode());
        return entity;
    }

    public static Brand toEntity(BrandDto dto, Brand entity) {
        entity.setName(dto.getName());
        entity.setLogo(dto.getLogo());
        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setCode(dto.getCode());
        return entity;
    }
}
