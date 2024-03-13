package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "project_booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectBooking extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", length = 255)
    private String projectName;


    @Column(name = "project_type", length = 255)
    private String projectType;

    @Column(name = "size", length = 255)
    private String size;

    @Column(name = "design_style", length = 255)
    private String designStyle;

    @Column(name = "colorSchemes", length = 255)
    private String colorSchemes;

    //muc dich su dung
    @Column(name = "intendUse", length = 255)
    private String intendUse;

    @Column(name = "occupants_number", length = 255)
    private String occupantsNumber;

    @Column(name = "timeLine", length = 255)
    private String timeLine;

    @Column(name = "projectPrice", length = 255)
    private String projectPrice;
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_project_id",nullable = false)
    private User user;

    @OneToOne (cascade = {CascadeType.ALL})
    @JoinColumn(name = "booking_project_id",nullable = false)
    private Booking booking;
}
