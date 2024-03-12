package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "userFullName")
    private String userFullName;

    @Column(name = "level")
    private int level;


    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    @JsonBackReference
    private Feedback feedback;

}
