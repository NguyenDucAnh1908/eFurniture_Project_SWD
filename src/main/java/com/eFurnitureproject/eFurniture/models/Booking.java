package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "streetAddress")
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

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_booking_id",nullable = false)
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "designer_id")
    @JsonManagedReference
    private User designer;
}
