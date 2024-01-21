package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vouchers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", length = 255)
    private String code;

    @Column(name = "discount_value", length = 255)
    private String discountValue;

    @Column(name = "minimum_purchase", length = 255)
    private String minimumPurchase;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "usage_limit")
    private int usageLimit;

    @Column(name = "used_count")
    private int usedCount;

    @Column(name = "status")
    private int status;

}
