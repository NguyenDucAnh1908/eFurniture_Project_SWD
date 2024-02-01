package com.eFurnitureproject.eFurniture.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDto {
    private int quantity;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    //@Max(value = 10000000, message = "Price must be less than or equal to 10,000,000")
    private double price;

    @Min(value = 0, message = "Total amount must be greater than or equal to 0")
    //@Max(value = 10000000, message = "Price must be less than or equal to 10,000,000")
    @JsonProperty("total_amount")
    private double totalAmount;

    private double discount;

    @Min(value = 1, message = "Product's ID must be > 0")
    private Long product;

    @Min(value = 1, message = "Orders must be greater than or equal to 0")
    private Long orders;
}
