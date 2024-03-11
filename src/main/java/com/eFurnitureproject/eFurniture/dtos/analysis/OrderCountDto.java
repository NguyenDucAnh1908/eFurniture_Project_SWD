package com.eFurnitureproject.eFurniture.dtos.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCountDto {
    private Long productId;
    private Integer orderCount;
}
