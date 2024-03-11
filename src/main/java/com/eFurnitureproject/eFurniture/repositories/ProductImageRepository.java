package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
    //List<ProductImages> findProductImagesById(Long productId);
    List<ProductImages> findByProduct(Product product);
    List<ProductImages> findByProductId(Long productId);
    Optional<ProductImages> findByIdAndProduct(Long imageId, Product product);
}
