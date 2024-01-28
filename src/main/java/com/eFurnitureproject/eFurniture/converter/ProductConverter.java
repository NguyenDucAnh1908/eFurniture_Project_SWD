package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.ProductDto;
import com.eFurnitureproject.eFurniture.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public static ProductDto toDto(Product entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setThumbnail(entity.getThumbnail());
        dto.setPrice(entity.getPrice());
        dto.setPriceSale(entity.getPriceSale());
        dto.setMaterial(entity.getMaterial());
        dto.setSize(entity.getSize());
        dto.setColor(entity.getColor());
        dto.setCodeProduct(entity.getCodeProduct());
        dto.setQuantitySold(entity.getQuantitySold());
        dto.setStatus(entity.getStatus());
        dto.setDiscount(entity.getDiscount());
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        Product entity = new Product();
        //entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setThumbnail(dto.getThumbnail());
        entity.setPrice(dto.getPrice());
        entity.setPriceSale(dto.getPriceSale());
        entity.setMaterial(dto.getMaterial());
        entity.setSize(dto.getSize());
        entity.setColor(dto.getColor());
        entity.setCodeProduct(dto.getCodeProduct());
        entity.setQuantitySold(dto.getQuantitySold());
        entity.setStatus(dto.getStatus());
        entity.setDiscount(dto.getDiscount());
//        entity.setCreatedAt(dto.getCreatedAt());
//        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public static Product toEntity(ProductDto dto, Product entity) {
        //Product entity = new Product();
        //entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setThumbnail(dto.getThumbnail());
        entity.setPrice(dto.getPrice());
        entity.setPriceSale(dto.getPriceSale());
        entity.setMaterial(dto.getMaterial());
        entity.setSize(dto.getSize());
        entity.setColor(dto.getColor());
        entity.setCodeProduct(dto.getCodeProduct());
        entity.setQuantitySold(dto.getQuantitySold());
        entity.setStatus(dto.getStatus());
        entity.setDiscount(dto.getDiscount());
        return entity;
    }
}
