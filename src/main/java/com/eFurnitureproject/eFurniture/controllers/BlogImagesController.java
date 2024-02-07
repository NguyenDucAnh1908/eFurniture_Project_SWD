package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.services.impl.BlogImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/blog-images")
@RequiredArgsConstructor
public class BlogImagesController {

    @Autowired
    private final BlogImageService blogImageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("blogId") Long blogId) {
        try {
            String imageUrl = blogImageService.uploadImage(file, blogId);
            return ResponseEntity.ok("Image uploaded successfully. URL: " + imageUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid blog ID: " + blogId);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }
}
