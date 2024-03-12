package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.ProductFavoriteResponse;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.ProductFavorite;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.ProductFavoriteRepository;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IProductFavoriteService;
import com.eFurnitureproject.eFurniture.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductFavoriteService implements IProductFavoriteService {
    private final ProductFavoriteRepository productFavoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;


    @Override
    public ResponseEntity<ProductFavoriteResponse> addProductToFavorite(Long productId) {
        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest()
                .getHeader("Authorization")
                .substring(7);
        String userEmail = jwtService.extractUsername(token);
        User requester = (User) userRepository.findUserByEmail(userEmail).orElse(null);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductFavorite productFavorite = ProductFavorite.builder()
                    .user(requester)
                    .product(product)
                    .favorite(true)
                    .build();
            productFavoriteRepository.save(productFavorite);
            return ResponseEntity.ok().body(ProductFavoriteResponse.builder()
                    .message("Add wish list")
                    .product(product)
                    .build());
        } else {
            return ResponseEntity.ok().body(ProductFavoriteResponse.builder()
                    .message("Add fail wish list")
                    .product(null)
                    .build());
        }
    }

    @Override
    public ResponseEntity<ProductFavoriteResponse> deleteProductToFavorite(Long productId) {
        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest()
                .getHeader("Authorization")
                .substring(7);
        String userEmail = jwtService.extractUsername(token);
        User requester = (User) userRepository.findUserByEmail(userEmail).orElse(null);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductFavorite productFavorite = ProductFavorite.builder()
                    .user(requester)
                    .product(product)
                    .favorite(false)
                    .build();
            productFavoriteRepository.save(productFavorite);
            return ResponseEntity.ok().body(ProductFavoriteResponse.builder()
                    .message("Delete wish list")
                    .product(product)
                    .build());
        } else {
            return ResponseEntity.ok().body(ProductFavoriteResponse.builder()
                    .message("Delete fail wish list")
                    .product(null)
                    .build());
        }
    }





}
