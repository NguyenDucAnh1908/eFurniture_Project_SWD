package com.eFurnitureproject.eFurniture.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFeedbackImagesService {
    public String uploadImage(MultipartFile file, Long feedbackId) throws IOException;
}
