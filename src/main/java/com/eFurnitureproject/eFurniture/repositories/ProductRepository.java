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

//    @Query("SELECT p FROM Product p " +
//            "WHERE (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) " +
//            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
//            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
//            "AND (:brandId IS NULL OR :brandId = 0 OR p.brand.id = :brandId) " +
//            "AND (:tagsProductId  IS NULL OR :tagsProductId = 0 OR p.tagsProduct.id = :tagsProductId) " +
//            "AND (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId)")
//    Page<Product> searchProducts(
//            @Param("keyword") String keyword, Pageable pageable,
//            @Param("minPrice") Double minPrice,
//            @Param("maxPrice") Double maxPrice,
//            @Param("brandId") Long brandId,
//            @Param("tagsProductId") Long tagsProductId,
//            @Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:brandIds IS NULL OR p.brand.id IN :brandIds) " +  // Sử dụng IN để kiểm tra nhiều giá trị
            "AND (:tagsProductIds IS NULL OR p.tagsProduct.id IN :tagsProductIds) " +
            "AND (:categoryIds IS NULL OR p.category.id IN :categoryIds)")
    Page<Product> searchProducts(
            @Param("keyword") String keyword, Pageable pageable,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("brandIds") List<Long> brandIds,  // Sử dụng List<Long> cho brandIds
            @Param("tagsProductIds") List<Long> tagsProductIds,
            @Param("categoryIds") List<Long> categoryIds);

<<<<<<< HEAD

    @Query("SELECT p FROM Product p " +
            "JOIN ProductFavorite pf ON p.id = pf.product.id " +
            "WHERE pf.favorite = true " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pf.product.id) DESC")
    List<Product> findTopFavoriteProducts();

    @Query(value = "SELECT p.* " +
            "FROM products p " +
            "JOIN favorite_product pf ON p.id = pf.product_id " +
            "WHERE pf.favorite = true " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(pf.product_id) DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Product> findTop5FavoriteProducts();
=======
>>>>>>> parent of d4fd3dc (Merge branch 'ducanh' into main)
}
