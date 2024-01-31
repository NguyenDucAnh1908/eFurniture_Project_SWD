package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.dtos.ProductImageDto;
import com.eFurnitureproject.eFurniture.services.impl.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/product_image")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<List<ProductImageDto>> uploadProductImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            List<ProductImageDto> uploadedImages = productImageService.uploadFiles(files, productId);
            return ResponseEntity.ok(uploadedImages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // or handle the error response as needed
        }
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<List<ProductImageDto>> updateProductImages(
//            //@RequestParam("productId") Long productId,
//            @PathVariable("id") Long productId,
//            @RequestParam("files") List<MultipartFile> files) {
//        try {
//            List<ProductImageDto> updatedImages = productImageService.updateFiles(files, productId);
//            return ResponseEntity.ok(updatedImages);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.emptyList()); // hoặc xử lý phản hồi lỗi theo nhu cầu
//        }
//    }
}
