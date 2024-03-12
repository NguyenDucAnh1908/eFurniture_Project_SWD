package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.ProductFavoriteResponse;

import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.services.IProductFavoriteService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ap1/v1/productFavorite")
@RequiredArgsConstructor
public class ProductFavoriteController {
    private final IProductFavoriteService productFavoriteService;

    @PostMapping("/add-wish-list/{productId}")
    public ResponseEntity<ProductFavoriteResponse> addProductToFavorite(@PathVariable Long productId) {
        try {
            return productFavoriteService.addProductToFavorite(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ProductFavoriteResponse.builder()
                            .message("Internal server error")
                            .product(null)
                            .build());
        }
    }

    @DeleteMapping("/delete-wish-list/{productId}")
    public ResponseEntity<ProductFavoriteResponse> deleteProductToFavorite(@PathVariable Long productId) {
        try {
            return productFavoriteService.deleteProductToFavorite(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ProductFavoriteResponse.builder()
                            .message("Internal server error")
                            .product(null)
                            .build());
        }
    }


}
