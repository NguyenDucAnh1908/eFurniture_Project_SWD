package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.ProductResponse;
import com.eFurnitureproject.eFurniture.converter.ProductConverter;
import com.eFurnitureproject.eFurniture.dtos.ProductDto;
import com.eFurnitureproject.eFurniture.dtos.Top5ProductDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.OrderStatsDTO;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Brand;
import com.eFurnitureproject.eFurniture.models.Category;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.TagsProduct;
import com.eFurnitureproject.eFurniture.repositories.*;
import com.eFurnitureproject.eFurniture.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final TagProductRepository tagProductRepository;
    private final FeedbackRepository feedbackRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product getProductById(long id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Cannot find product with id =" + id);
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
        double discount = productDto.getDiscount() != null ? productDto.getDiscount() : 0.0;
        double priceSale = product.getPriceSale();
        double price = priceSale * ((100 - discount) / 100);
        product.setPrice(price);
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
            double discount = productDto.getDiscount() != null ? productDto.getDiscount() : 0.0;
            double priceSale = productDto.getPriceSale();
            double price = priceSale * ((100 - discount) / 100);
            existingProduct.setPrice(price);
            existingProduct.setCodeProduct(generatedCode);
            existingProduct.setCategory(existingCategory);
            existingProduct.setBrand(existingBrand);
            existingProduct.setTagsProduct(existingProductTag);
            existingProduct = productRepository.save(existingProduct);
            return existingProduct;
        }
        return null;
    }

    public Page<ProductResponse> getAllProducts(String keyword, PageRequest pageRequest,
                                                Double minPrice, Double maxPrice,
                                                List<Long> brandIds, List<Long> tagsProductIds, List<Long> categoryIds) {
        Page<Product> products;
        products = productRepository.searchProducts(
                keyword, pageRequest, minPrice, maxPrice, brandIds, tagsProductIds, categoryIds);
        return products.map(product -> {
            ProductResponse response = ProductConverter.toResponse(product);
            Double averageRating = feedbackRepository.findAverageRatingByProductId(product.getId());
            response.setRating(averageRating);
            return response;
        });
    }

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    ProductResponse response = ProductConverter.toResponse(product);
                    Double averageRating = feedbackRepository.findAverageRatingByProductId(product.getId());
                    response.setRating(averageRating);
                    return response;
                })
                .collect(Collectors.toList());
//        return products.map(product -> {
//            ProductResponse response = ProductConverter.toResponse(product);
//            Double averageRating = feedbackRepository.findAverageRatingByProductId(product.getId());
//            response.setRating(averageRating);
//            return response;
//        });
    }

    public List<ProductResponse> getProductByCategory(Long id) {
        List<Product> products = productRepository.findByCategoryId(id);
        return products.stream()
                .map(product -> {
                    ProductResponse response = ProductConverter.toResponse(product);
                    Double averageRating = feedbackRepository.findAverageRatingByProductId(product.getId());
                    response.setRating(averageRating);
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Top5ProductDto> getTop5BestSellingProducts() {
        List<Object[]> topProducts = orderDetailRepository.findTop5BestSellingProducts();
        return topProducts.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findtop5() {
        List<Product> top5 = productRepository.findTop5ProductsByTotalSold();
        return top5;
    }


    private Top5ProductDto mapToProductDto(Object[] result) {
        Top5ProductDto productDto = new Top5ProductDto();
        productDto.setProduct((Product) result[0]);
        productDto.setTotalQuantitySold(((Long) result[1]).intValue()); // Chuyển đổi Long sang Integer
        productDto.setTotalAmountSold((Double) result[2]);
        return productDto;
    }


    private String generateCodeFromName(String codeProduct) {
        return codeProduct.replaceAll("\\s", "-").toLowerCase();
    }
}
