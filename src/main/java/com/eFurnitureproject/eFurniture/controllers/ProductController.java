package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.ProductCreateResponse;
import com.eFurnitureproject.eFurniture.Responses.ProductListResponse;
import com.eFurnitureproject.eFurniture.Responses.ProductResponse;
import com.eFurnitureproject.eFurniture.components.LocalizationUtils;
import com.eFurnitureproject.eFurniture.dtos.ProductDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.services.impl.ProductService;
import com.eFurnitureproject.eFurniture.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDto productDTO,
            BindingResult result
    ) throws DataNotFoundException {
        ProductCreateResponse productCreateResponse = new ProductCreateResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            productCreateResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            productCreateResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(productCreateResponse);
        }
        Product tagsProduct = productService.createProduct(productDTO);
        productCreateResponse.setProduct(tagsProduct);
        return ResponseEntity.ok(productCreateResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDTO,
            BindingResult result
    ) throws Exception {
        ProductCreateResponse productCreateResponse = new ProductCreateResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            productCreateResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            productCreateResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(productCreateResponse);
        }
        Product tagsProduct = productService.updateProduct(id, productDTO);
        productCreateResponse.setProduct(tagsProduct);
        return ResponseEntity.ok(productCreateResponse);
    }

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getAllProduct(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "brandId", required = false) Long brandId,
            @RequestParam(value = "tagsProductId", required = false) Long tagsProductId,
            @RequestParam(value = "categoryId", required = false) Long categoryId
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").descending()
        );
        Page<ProductResponse> productPage = productService.getAllProducts(
                keyword, pageRequest, minPrice, maxPrice, brandId, tagsProductId, categoryId);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }

}
