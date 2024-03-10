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

    @Column(name = "code-design", length = 500, nullable = true)
    private String codeDesign;

    @Column(name = "staffName", length = 500, nullable = true)
    private String staffName;

    @Column(name = "image_urls", columnDefinition = "TEXT", nullable = true)
    private String imageUrls;

    @Column(name = "status", length = 500, nullable = true)
    private String status;

    @Column(name = "note", columnDefinition = "TEXT", nullable = true)
    private String note;


    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] fileData;

    @Column(name = "file_name", nullable = true)
    private String fileName;

    @Column(name = "file_type", nullable = true)
    private String fileType;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "projectBooking_id")
    private ProjectBooking projectBooking;
}
