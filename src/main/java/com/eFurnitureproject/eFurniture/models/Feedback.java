package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "reply")
    private String reply;

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
