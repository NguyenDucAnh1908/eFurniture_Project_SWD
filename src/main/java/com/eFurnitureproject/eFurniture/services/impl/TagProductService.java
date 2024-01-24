package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.TagProductConverter;
import com.eFurnitureproject.eFurniture.dtos.TagProductDto;
import com.eFurnitureproject.eFurniture.models.TagsProduct;
import com.eFurnitureproject.eFurniture.repositories.TagProductRepository;
import com.eFurnitureproject.eFurniture.services.ITagProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagProductService implements ITagProductService {
    private final TagProductRepository tagProductRepository;

    @Override
    public TagsProduct createTagsProduct(TagProductDto tagProductDto) {
        String generatedCode = generateCodeFromName(tagProductDto.getName());
        TagsProduct tagsProduct = TagProductConverter.toEntity(tagProductDto);
        tagsProduct.setCode(generatedCode);
        tagsProduct = tagProductRepository.save(tagsProduct);
        return tagsProduct;
    }

    @Override
    public TagsProduct updateTagsProduct(Long id, TagProductDto brandDto) {
        String generatedCode = generateCodeFromName(brandDto.getName());
        TagsProduct existingTagsProduct = getTagsProductById(id);
        TagProductConverter.toEntity(brandDto, existingTagsProduct);
        existingTagsProduct.setCode(generatedCode);
        existingTagsProduct = tagProductRepository.save(existingTagsProduct);
        return existingTagsProduct;
    }

    @Override
    public TagsProduct getTagsProductById(long id) {
        return tagProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag product not found"));
    }

    @Override
    public List<TagsProduct> getAllTagsProducts() {
        return tagProductRepository.findAll();
    }

    private String generateCodeFromName(String name) {
        return name.replaceAll("\\s", "-").toLowerCase();
    }
}
