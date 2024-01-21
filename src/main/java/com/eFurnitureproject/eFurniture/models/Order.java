package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_Date")
    private LocalDate orderDate;

    @Column(name = "status")
    private int status;

    @Column(name = "total_Amount")
    private double totalAmount;

    @Column(name = "payment_method", length = 255)
    private String paymentMethod;

    @Column(name = "shipping_address", length = 255)
    private String shippingAddress;

    @Column(name = "tracking_number", length = 255)
    private String trackingNumber;

    @Column(name = "shipping_method", length = 255)
    private String shippingMethod;

    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "discounts", length = 255)
    private String discounts;

    @Column(name = "fullname", length = 255)
    private String fullName;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "active")
    private int active;
}
