package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.ProductConverter;
import com.eFurnitureproject.eFurniture.dtos.ProductDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Brand;
import com.eFurnitureproject.eFurniture.models.Category;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.TagsProduct;
import com.eFurnitureproject.eFurniture.repositories.BrandRepository;
import com.eFurnitureproject.eFurniture.repositories.CategoryRepository;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.repositories.TagProductRepository;
import com.eFurnitureproject.eFurniture.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final TagProductRepository tagProductRepository;

    @Override
    public Product getProductById(long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id =" + 5);
    }

    @Override
    @Transactional
    public Product createProduct(ProductDto productDto) throws DataNotFoundException {
        String generatedCode = generateCodeFromName(productDto.getName());
        Category existingCategory = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: " + productDto.getCategoryId()));
        Brand existingBrand = brandRepository
                .findById(productDto.getBrandId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: " + productDto.getBrandId()));
        TagsProduct existingProductTag = tagProductRepository
                .findById(productDto.getTagsProductId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find category with id: " + productDto.getTagsProductId()));

        Product product = ProductConverter.toEntity(productDto);
        product.setCodeProduct(generatedCode);
        product.setCategory(existingCategory);
        product.setBrand(existingBrand);
        product.setTagsProduct(existingProductTag);
        product = productRepository.save(product);
        return product;
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDto productDto) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            String generatedCode = generateCodeFromName(productDto.getName());
            Category existingCategory = categoryRepository
                    .findById(productDto.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " + productDto.getCategoryId()));
            Brand existingBrand = brandRepository
                    .findById(productDto.getBrandId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " + productDto.getBrandId()));
            TagsProduct existingProductTag = tagProductRepository
                    .findById(productDto.getTagsProductId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find category with id: " + productDto.getTagsProductId()));
            ProductConverter.toEntity(productDto, existingProduct);
            existingProduct.setCodeProduct(generatedCode);
            existingProduct.setCategory(existingCategory);
            existingProduct.setBrand(existingBrand);
            existingProduct.setTagsProduct(existingProductTag);
            existingProduct = productRepository.save(existingProduct);
            return existingProduct;
        }
        return null;
    }

    private String generateCodeFromName(String codeProduct) {
        return codeProduct.replaceAll("\\s", "-").toLowerCase();
    }
}
