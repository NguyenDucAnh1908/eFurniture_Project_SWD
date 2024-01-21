package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "thumbnail", length = 255)
    private String thumbnail;

    @Column(name = "price")
    private double price;

    @Column(name = "price_sale")
    private double priceSale;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "material", length = 255)
    private String material;

    @Column(name = "size", length = 255)
    private String size;

    @Column(name = "color")
    private int color;

    @Column(name = "code_product", length = 255)
    private String codeProduct;

    @Column(name = "quantity_sold")
    private int quantitySold;

    @Column(name = "status")
    private int status;

    @Column(name = "discount", length = 255)
    private String discount;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_category",nullable = false)
    private Category category;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_brand",nullable = false)
    private Brand brand;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_tags",nullable = false)
    private TagsProduct tagsProduct;
}
