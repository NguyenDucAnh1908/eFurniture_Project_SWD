package com.eFurnitureproject.eFurniture.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eFurnitureproject.eFurniture.dtos.ProductImageDto;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.ProductImages;
import com.eFurnitureproject.eFurniture.repositories.ProductImageRepository;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.services.IProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {
    private final ProductImageRepository productImageRepository;
    private final Cloudinary cloudinary;
    private final ProductRepository productRepository;
    private final ProductService productService;


    @Override
    @Transactional
    public List<ProductImageDto> uploadFiles(List<MultipartFile> gifs, Long productId) {
        try {
            List<ProductImageDto> imageDtos = new ArrayList<>();

            // Ensure that the number of images doesn't exceed the limit
            if (gifs.size() > 5) {
                throw new IllegalArgumentException("Exceeded the maximum limit of 5 images per request");
            }

            for (MultipartFile gif : gifs) {
                File uploadedFile = convertMultiPartToFile(gif);
                Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
                boolean isDeleted = uploadedFile.delete();

                if (isDeleted) {
                    System.out.println("File successfully deleted");
                } else {
                    System.out.println("File doesn't exist");
                }

                String imageUrl = uploadResult.get("url").toString();

                // Save the information to the database with the associated product
                ProductImageDto imageDto = saveToDatabase(imageUrl, productId);
                imageDtos.add(imageDto);
            }

            return imageDtos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private ProductImageDto saveToDatabase(String imageUrl, Long productId) throws Exception {
        // Fetch the Product by its ID
        Product product = productService.getProductById(productId);

        // Create a ProductImages entity and associate it with the product
        ProductImages productImage = new ProductImages();
        productImage.setImageUrl(imageUrl);
        productImage.setProduct(product);

        // Save the ProductImages entity to the database
        productImageRepository.save(productImage);

        // Convert the saved entity to a DTO for response
        return ProductImageDto.builder()
                .productId(productImage.getProduct().getId())
                .imageUrl(productImage.getImageUrl())
                .build();
    }

    public List<ProductImages> getImagesByProduct (Long id){
        return productImageRepository.findByProductId(id);
    }
//@Override
//@Transactional
//public List<ProductImageDto> uploadFiles(List<MultipartFile> gifs, Long productId) {
//    try {
//        List<ProductImageDto> imageDtos = new ArrayList<>();
//
//        // Ensure that the number of images doesn't exceed the limit
//        if (gifs.size() > 5) {
//            throw new IllegalArgumentException("Exceeded the maximum limit of 5 images per request");
//        }
//
//        // Fetch the existing images for the product
//        List<ProductImages> existingImages = productImageRepository.findByProductId(productId);
//
//        // Delete existing images from cloudinary and strong database
//        deleteExistingImages(existingImages);
//
//        for (MultipartFile gif : gifs) {
//            File uploadedFile = convertMultiPartToFile(gif);
//            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
//            boolean isDeleted = uploadedFile.delete();
//
//            if (isDeleted) {
//                System.out.println("File successfully deleted");
//            } else {
//                System.out.println("File doesn't exist");
//            }
//
//            String imageUrl = uploadResult.get("url").toString();
//
//            // Save the information to the database with the associated product
//            ProductImageDto imageDto = saveToDatabase(imageUrl, productId);
//            imageDtos.add(imageDto);
//        }
//
//        return imageDtos;
//    } catch (Exception e) {
//        throw new RuntimeException(e);
//    }
//}
//
//    private void deleteExistingImages(List<ProductImages> existingImages) {
//        for (ProductImages existingImage : existingImages) {
//            // Delete image from cloudinary
//            deleteImageFromCloudinary(existingImage.getImageUrl());
//
//            // Delete image from strong database
//            productImageRepository.delete(existingImage);
//        }
//    }
//
//    private void deleteImageFromCloudinary(String imageUrl) {
//        // Implement the logic to delete the image from cloudinary
//        // Use cloudinary.api().deleteResources method or similar based on your Cloudinary configuration
//    }
//
//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
//
//    private ProductImageDto saveToDatabase(String imageUrl, Long productId) throws Exception {
//        // Fetch the Product by its ID
//        Product product = productService.getProductById(productId);
//
//        // Create a ProductImages entity and associate it with the product
//        ProductImages productImage = new ProductImages();
//        productImage.setImageUrl(imageUrl);
//        productImage.setProduct(product);
//
//        // Save the ProductImages entity to the database
//        productImageRepository.save(productImage);
//
//        // Convert the saved entity to a DTO for response
//        return ProductImageDto.builder()
//                .productId(productImage.getProduct().getId())
//                .imageUrl(productImage.getImageUrl())
//                .build();
//    }


}
