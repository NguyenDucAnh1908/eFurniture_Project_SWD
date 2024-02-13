package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "street_address", length = 500)
    private String streetAddress;

    @Column(name = "province", length = 255)
    private String province;

    @Column(name = "country", length = 255)
    private String country;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "postal_code", length = 255)
    private String postalCode;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_address",nullable = false)
    @JsonBackReference
    private User user;

    // Getters and setters

}
