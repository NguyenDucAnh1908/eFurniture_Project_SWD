package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public Product getProductById(long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id =" + 5);
    }
}
