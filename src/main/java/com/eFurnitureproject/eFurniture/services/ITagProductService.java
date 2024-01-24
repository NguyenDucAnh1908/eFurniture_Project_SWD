package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.TagProductDto;
import com.eFurnitureproject.eFurniture.models.TagsProduct;

import java.util.List;

public interface ITagProductService {
    TagsProduct createTagsProduct(TagProductDto tagProductDto);

    TagsProduct updateTagsProduct(Long id, TagProductDto brandDto);

    TagsProduct getTagsProductById(long id);

    List<TagsProduct> getAllTagsProducts();
}
