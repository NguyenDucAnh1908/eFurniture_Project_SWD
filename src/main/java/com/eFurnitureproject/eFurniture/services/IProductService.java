package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.ProductDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(long id) throws Exception;
    Product createProduct(ProductDto productDto) throws DataNotFoundException;
    Product updateProduct(Long id, ProductDto productDto) throws Exception;
    List<Product> getProductByCategory(Long id);
    List<Product> getAllProduct();

}
