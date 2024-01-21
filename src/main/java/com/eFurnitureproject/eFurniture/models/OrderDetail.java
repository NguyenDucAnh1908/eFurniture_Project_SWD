package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "total_amount")
    private double totalAmount;


    @Column(name = "discount")
    private double discount;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;


    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
}
