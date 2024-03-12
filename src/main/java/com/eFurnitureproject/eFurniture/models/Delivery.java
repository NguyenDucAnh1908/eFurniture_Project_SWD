package com.eFurnitureproject.eFurniture.models;

import com.eFurnitureproject.eFurniture.models.Enum.StatusDelivery;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private StatusDelivery deliveryStatus;

    @Column(name = "tracking_number")
    private String trackingNumber;
}
