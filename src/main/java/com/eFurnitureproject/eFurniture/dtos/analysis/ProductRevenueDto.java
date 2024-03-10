package com.eFurnitureproject.eFurniture.dtos.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRevenueDto {
    private Long productId;
    private Double totalRevenue;
}
