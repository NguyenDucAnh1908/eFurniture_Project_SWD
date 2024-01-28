package com.eFurnitureproject.eFurniture.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tags_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagsProduct extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    private String code;
//    @OneToMany(mappedBy = "tagsProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Product> products;
}
