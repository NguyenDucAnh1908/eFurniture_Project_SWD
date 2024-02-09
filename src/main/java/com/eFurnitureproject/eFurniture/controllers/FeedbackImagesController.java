package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.services.IFeedbackImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/feedback-images")
@RequiredArgsConstructor
public class FeedbackImagesController {

    @Autowired
    private final IFeedbackImagesService feedbackImagesService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("blogId") Long blogId) {
        try {
            String imageUrl = feedbackImagesService.uploadImage(file, blogId);
            return ResponseEntity.ok("Image uploaded successfully. URL: " + imageUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid blog ID: " + blogId);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }
}