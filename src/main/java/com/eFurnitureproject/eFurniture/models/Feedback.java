package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "status")
    private String status;

    @Column(name = "reply", columnDefinition = "TEXT", nullable = true)
    private String reply;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "replier_id", nullable = true)
    private User replier;

    @Column(name = "image_urls", columnDefinition = "TEXT", nullable = true)
    private String imageUrls;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_feedback",nullable = false)
    @JsonManagedReference
    private User user;
}
