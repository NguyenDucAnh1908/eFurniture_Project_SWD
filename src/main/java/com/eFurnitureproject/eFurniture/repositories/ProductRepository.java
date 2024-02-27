package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);
    List<Product> findByCategoryId(Long id);
    //Optional<Product> findById

    @Query("SELECT p FROM Product p " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:brandId IS NULL OR :brandId = 0 OR p.brand.id = :brandId) " +
            "AND (:tagsProductId  IS NULL OR :tagsProductId = 0 OR p.tagsProduct.id = :tagsProductId) " +
            "AND (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId)")
    Page<Product> searchProducts(
            @Param("keyword") String keyword, Pageable pageable,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("brandId") Long brandId,
            @Param("tagsProductId") Long tagsProductId,
            @Param("categoryId") Long categoryId);


}
