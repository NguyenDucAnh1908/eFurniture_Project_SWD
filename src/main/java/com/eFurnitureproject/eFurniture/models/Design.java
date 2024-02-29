package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "design")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Design extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_urls", columnDefinition = "TEXT", nullable = true)
    private String imageUrls;

    @Column(name = "status", length = 500, nullable = true)
    private String status;

    @Column(name = "note", columnDefinition = "TEXT", nullable = true)
    private String note;


    @Column(name = "url_file_data",columnDefinition = "TEXT", nullable = true)
    private String urlFileData;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "projectBooking_id")
    private ProjectBooking projectBooking;
}
