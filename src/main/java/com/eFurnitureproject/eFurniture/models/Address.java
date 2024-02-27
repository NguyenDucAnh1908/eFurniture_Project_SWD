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

    @Column(name = "wardCode", length = 500)
    private String wardCode;

    @Column(name = "districtCode", length = 500)
    private String districtCode;

    @Column(name = "province", length = 500)
    private String provinceCode;

    @Column(name = "wardName", length = 500)
    private String wardName;

    @Column(name = "districtName", length = 500)
    private String districtName;

    @Column(name = "provinceName", length = 500)
    private String provinceName;

    @Column(name = "status", length = 500, nullable = true)
    private String status;

    @Column(name = "phone_number", length = 500)
    private String phoneNumber;


    @ManyToOne
    @JoinColumn(name = "user_address",nullable = false)
    @JsonBackReference
    private User user;

    public void setUser(User user) {
        this.user = user;
    }


}
