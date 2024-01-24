package com.eFurnitureproject.eFurniture.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags_Blog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagsBlog  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag_name")
    private String tagName;
    private String code;
}
