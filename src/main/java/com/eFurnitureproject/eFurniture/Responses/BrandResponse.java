package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.Brand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    private Brand brand;
}
