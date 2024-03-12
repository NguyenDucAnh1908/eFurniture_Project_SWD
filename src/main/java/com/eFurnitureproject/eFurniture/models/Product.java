package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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


//    @Column(name = "rating")
//    private Double rating;

    @Column(name = "code_product", length = 255)
    private String codeProduct;

    @Column(name = "quantity_sold")
    private int quantitySold;

    @Column(name = "status")
    private int status;

    @Column(name = "discount", length = 255)
    private Double discount;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_category",nullable = false)
    //@JsonBackReference
    private Category category;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_brand",nullable = false)
    //@JsonBackReference
    private Brand brand;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_tags",nullable = false)
    //@JsonBackReference
    private TagsProduct tagsProduct;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProductImages> productImages;

}
