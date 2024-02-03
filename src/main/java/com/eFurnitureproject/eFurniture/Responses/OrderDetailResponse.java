package com.eFurnitureproject.eFurniture.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private int quantity;
    private double price;
    @JsonProperty("total_amount")
    private double totalAmount;

    private double discount;

    private Long product;

    private Long orders;
}
