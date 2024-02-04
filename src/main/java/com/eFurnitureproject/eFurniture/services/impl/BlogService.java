package com.eFurnitureproject.eFurniture.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eFurnitureproject.eFurniture.Responses.BlogResponse;
import com.eFurnitureproject.eFurniture.converter.BlogConverter;
import com.eFurnitureproject.eFurniture.dtos.BlogDto;
import com.eFurnitureproject.eFurniture.models.Blog;
import com.eFurnitureproject.eFurniture.models.CategoryBlog;
import com.eFurnitureproject.eFurniture.models.TagsBlog;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.BlogRepository;
import com.eFurnitureproject.eFurniture.repositories.CategoryBlogRepository;
import com.eFurnitureproject.eFurniture.repositories.TagsBlogRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IBlogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService implements IBlogService {
    private final BlogRepository blogRepository;
    private  final CategoryBlogRepository categoryBlogRepository;
    private final UserRepository userRepository;
    private final TagsBlogRepository tagsBlogRepository;
    @Autowired
    private Cloudinary cloudinary;


    @Override
    public Page<BlogResponse> getAllBlogs(String keyword, Pageable pageable,
                                          Long userBlogId, Long tagsBlogId) {
        Page<Blog> blogs = blogRepository.searchBlogs(
                keyword, pageable, userBlogId, tagsBlogId);

        return blogs.map(BlogConverter::toResponse);
    }

    @Override
    public Blog createBlog(BlogDto blogDto) throws EntityNotFoundException {
        CategoryBlog existingCategory = categoryBlogRepository
                .findById(blogDto.getCategoryBlogId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Cannot find category with id: " + blogDto.getCategoryBlogId()));
        User existingUser = userRepository
                .findById(blogDto.getUserBlogId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Cannot find user with id: " + blogDto.getUserBlogId()));
        TagsBlog existingTagsBlog = tagsBlogRepository
                .findById(blogDto.getTagBlogId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Cannot find tagsBlog with id: " + blogDto.getTagBlogId()));

        Blog newBlog = Blog.builder()
                .title(blogDto.getTitle())
                .content(blogDto.getContent())
                .thumbnail(blogDto.getThumbnail())
                .categoryBlog(existingCategory)
                .user(existingUser)
                .tagsBlog(existingTagsBlog)
                .active(true)
                .build();

        Blog savedBlog = blogRepository.save(newBlog);
        return savedBlog;
    }



    @Override
    public Blog updateBlog(Long blogId, BlogDto updatedBlogDto) throws EntityNotFoundException {
        Blog existingBlog = blogRepository.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find blog with id: " + blogId));

        Optional<Blog> blog = blogRepository.findByIdAndActive(blogId, true);

        if(blog.isPresent()) {
            if (updatedBlogDto.getTitle() != null) {
                existingBlog.setTitle(updatedBlogDto.getTitle());
            }
            if (updatedBlogDto.getContent() != null) {
                existingBlog.setContent(updatedBlogDto.getContent());
            }
            if (updatedBlogDto.getThumbnail() != null) {
                existingBlog.setThumbnail(updatedBlogDto.getThumbnail());
            }

            if (!existingBlog.getCategoryBlog().getId().equals(updatedBlogDto.getCategoryBlogId())) {
                CategoryBlog existingCategory = categoryBlogRepository
                        .findById(updatedBlogDto.getCategoryBlogId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Cannot find category with id: " + updatedBlogDto.getCategoryBlogId()));
                existingBlog.setCategoryBlog(existingCategory);
            }

            if (!existingBlog.getUser().getId().equals(updatedBlogDto.getUserBlogId())) {
                User existingUser = userRepository
                        .findById(updatedBlogDto.getUserBlogId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Cannot find user with id: " + updatedBlogDto.getUserBlogId()));
                existingBlog.setUser(existingUser);
            }

            if (!existingBlog.getTagsBlog().getId().equals(updatedBlogDto.getTagBlogId())) {
                TagsBlog existingTagsBlog = tagsBlogRepository
                        .findById(updatedBlogDto.getTagBlogId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Cannot find tagsBlog with id: " + updatedBlogDto.getTagBlogId()));
                existingBlog.setTagsBlog(existingTagsBlog);
            }
//            updatedBlogDto.setActive(true);
            return blogRepository.save(existingBlog);
        }
        else{
            return null;
        }
    }

    public String uploadThumbnailToCloudinary(Long blogId, MultipartFile image) throws IOException {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with id: " + blogId));

        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        // Cập nhật URL vào đối tượng Blog và lưu vào database
        blog.setThumbnail(imageUrl);
        blogRepository.save(blog);

        return imageUrl;
    }


    @Override
    public BlogResponse DeactivateBlog(Long blogId) throws EntityNotFoundException {
        Blog existingBlog = blogRepository.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find blog with id: " + blogId));

        existingBlog.setActive(false);
        Blog deletedBlog = blogRepository.save(existingBlog);

        return BlogConverter.toResponse(deletedBlog);
    }


}
