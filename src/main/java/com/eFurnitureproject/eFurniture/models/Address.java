package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_address", length = 255)
    private String streetAddress;

    @Column(name = "city", length = 255)
    private String city;

    @Column(name = "state", length = 255)
    private String state;

    @Column(name = "country", length = 255)
    private String country;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "address_type", length = 255)
    private String addressType;

    @Column(name = "default_address")
    private int defaultAddress;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_address",nullable = false)
    @JsonBackReference
    private User user;

    // Getters and setters

}
