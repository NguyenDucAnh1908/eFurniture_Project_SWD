package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", length = 16383)
    private String content;

    @Column(name = "thumbnail", length = 255, nullable = true)
    private String thumbnail;

    @Column(name = "image_urls", length = 1024, nullable = true)
    private String imageUrls;


    @Column(name = "is_active")
    private boolean active;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "category_blog",nullable = true)
    private CategoryBlog categoryBlog;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_blog_id",nullable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "tag_blog_id",nullable = true)
    private TagsBlog tagsBlog;


}
