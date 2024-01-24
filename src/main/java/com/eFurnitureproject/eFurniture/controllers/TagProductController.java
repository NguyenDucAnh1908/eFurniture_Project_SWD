package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.TagProductResponse;
import com.eFurnitureproject.eFurniture.components.LocalizationUtils;
import com.eFurnitureproject.eFurniture.dtos.TagProductDto;
import com.eFurnitureproject.eFurniture.models.TagsProduct;
import com.eFurnitureproject.eFurniture.services.impl.TagProductService;
import com.eFurnitureproject.eFurniture.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tag_product")
@RequiredArgsConstructor
public class TagProductController {
    private final TagProductService tagProductService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<TagProductResponse> createTagProduct(
            @Valid @RequestBody TagProductDto tagProductDTO,
            BindingResult result
    ) {
        TagProductResponse tagProductResponse = new TagProductResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            tagProductResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            tagProductResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(tagProductResponse);
        }
        TagsProduct tagsProduct = tagProductService.createTagsProduct(tagProductDTO);
        tagProductResponse.setTagsProduct(tagsProduct);
        return ResponseEntity.ok(tagProductResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagProductResponse> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody TagProductDto tagProductDTO,
            BindingResult result
    ) {
        TagProductResponse tagProductResponse = new TagProductResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            tagProductResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            tagProductResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(tagProductResponse);
        }
        TagsProduct tagsProduct = tagProductService.updateTagsProduct(id, tagProductDTO);
        tagProductResponse.setTagsProduct(tagsProduct);
        return ResponseEntity.ok(tagProductResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<TagsProduct>> getAllTagsProducts() {
        List<TagsProduct> tagsProducts = tagProductService.getAllTagsProducts();
        return ResponseEntity.ok(tagsProducts);
    }
}
