package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.ProductListFavorite;
import com.eFurnitureproject.eFurniture.Responses.ProductListResponse;
import com.eFurnitureproject.eFurniture.Responses.ProductResponse;
import com.eFurnitureproject.eFurniture.dtos.ProductDto;
import com.eFurnitureproject.eFurniture.dtos.ProductImageDto;
import com.eFurnitureproject.eFurniture.dtos.Top5ProductDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.OrderStatsDTO;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {
    Product getProductById(long id) throws Exception;
    Product createProduct(ProductDto productDto) throws DataNotFoundException;
    Product updateProduct(Long id, ProductDto productDto) throws Exception;
    List<ProductResponse> getProductByCategory(Long id);
    List<Product> getAllProduct();
    List<Top5ProductDto> getTop5BestSellingProducts();



    ResponseEntity<ProductListFavorite> findTopFavoriteProducts();

    List<ProductDto> findTop5FavoriteProducts();
}
