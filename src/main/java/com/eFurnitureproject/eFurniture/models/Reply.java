package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "replies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "replyText", columnDefinition = "TEXT", nullable = true)
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    @JsonBackReference
    private Feedback feedback;

    @ManyToOne
    @JoinColumn(name = "replier_id", nullable = true)
    private User replier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
