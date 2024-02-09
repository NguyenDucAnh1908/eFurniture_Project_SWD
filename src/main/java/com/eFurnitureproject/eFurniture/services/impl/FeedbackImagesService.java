package com.eFurnitureproject.eFurniture.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eFurnitureproject.eFurniture.models.Blog;
import com.eFurnitureproject.eFurniture.models.BlogImages;
import com.eFurnitureproject.eFurniture.models.Feedback;
import com.eFurnitureproject.eFurniture.models.FeedbackImages;
import com.eFurnitureproject.eFurniture.repositories.BlogImagesRepository;
import com.eFurnitureproject.eFurniture.repositories.BlogRepository;
import com.eFurnitureproject.eFurniture.repositories.FeedbackImagesRepository;
import com.eFurnitureproject.eFurniture.repositories.FeedbackRepository;
import com.eFurnitureproject.eFurniture.services.IFeedbackImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class FeedbackImagesService implements IFeedbackImagesService {
    @Autowired
    private Cloudinary cloudinary;

    private final FeedbackImagesRepository feedbackImagesRepository;

    private final FeedbackRepository feedbackRepository;

    @Override
    public String uploadImage(MultipartFile file, Long feedbackId) throws IOException {
        // Find feedback
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid feedback ID: " + feedbackId));

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String imageUrl = (String) uploadResult.get("url");

        FeedbackImages feedbackImages = new FeedbackImages();
        feedbackImages.setImageUrl(imageUrl);
        feedbackImages.setFeedback(feedback);
        feedbackImagesRepository.save(feedbackImages);

        return imageUrl;
    }
}
