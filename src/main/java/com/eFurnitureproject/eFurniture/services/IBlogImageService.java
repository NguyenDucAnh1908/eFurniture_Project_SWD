package com.eFurnitureproject.eFurniture.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IBlogImageService {
    String uploadImage(MultipartFile file, Long blogId) throws IOException;

}
