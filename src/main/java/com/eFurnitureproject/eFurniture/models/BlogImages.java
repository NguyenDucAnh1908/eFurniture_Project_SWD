package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogImages extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "blog_id", nullable = false)
    @JsonIgnore
    @JsonManagedReference
    private Blog blog;
}