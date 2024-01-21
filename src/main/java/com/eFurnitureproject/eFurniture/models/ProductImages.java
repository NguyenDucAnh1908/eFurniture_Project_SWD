package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImages extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_url", length = 255)
    private String imageUrl;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
}
