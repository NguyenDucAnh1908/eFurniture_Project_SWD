package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.models.Product;

public interface IProductService {
    Product getProductById(long id) throws Exception;
}
