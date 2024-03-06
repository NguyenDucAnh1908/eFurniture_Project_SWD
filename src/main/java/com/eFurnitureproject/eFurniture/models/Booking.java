package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "streetAddress", length = 255)
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

    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "note", length = 255)
    private String note;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_booking_id",nullable = false)
    private User user;

}
