package com.eFurnitureproject.eFurniture.Responses.ChartResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class SalesChartDataResponse {
    private LocalDate orderDate;
    private double totalAmount;
}
