package com.eFurnitureproject.eFurniture.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eFurnitureproject.eFurniture.models.Blog;
import com.eFurnitureproject.eFurniture.models.BlogImages;
import com.eFurnitureproject.eFurniture.repositories.BlogImagesRepository;
import com.eFurnitureproject.eFurniture.repositories.BlogRepository;
import com.eFurnitureproject.eFurniture.services.IBlogImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlogImageService implements IBlogImageService {

    @Autowired
    private Cloudinary cloudinary;

    private final BlogImagesRepository blogImagesRepository;

    private final BlogRepository blogRepository;

    @Override
    public String uploadImage(MultipartFile file, Long blogId) throws IOException {
        // Tìm bài đăng với blogId tương ứng
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog ID: " + blogId));

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String imageUrl = (String) uploadResult.get("url");

        BlogImages blogImage = new BlogImages();
        blogImage.setImageUrl(imageUrl);
        blogImage.setBlog(blog);
        blogImagesRepository.save(blogImage);

        return imageUrl;
    }
}