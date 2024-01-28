package com.eFurnitureproject.eFurniture.models;

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

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "date_feedback")
    private LocalDate dateFeedback;

    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "reply", length = 255)
    private String reply;

    @Column(name = "images", length = 255)
    private String images;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

//    @OneToMany(mappedBy = "feedbacks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<FeedbackImages> feedbackImages;
}
