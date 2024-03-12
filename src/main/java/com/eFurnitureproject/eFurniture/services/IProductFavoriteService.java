package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.ProductFavoriteResponse;
import com.eFurnitureproject.eFurniture.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductFavoriteService {
    ResponseEntity<ProductFavoriteResponse> addProductToFavorite(Long productId);

    ResponseEntity<ProductFavoriteResponse> deleteProductToFavorite(Long productId);


}
