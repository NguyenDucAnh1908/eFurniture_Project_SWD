package com.eFurnitureproject.eFurniture.dtos;

import com.eFurnitureproject.eFurniture.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDto extends BaseEntity {
    @NotEmpty(message = "Brand's name cannot be empty")
    private String name;
    private String logo;
    @JsonProperty("website_url")
    private String websiteUrl;
    private String code;
}
