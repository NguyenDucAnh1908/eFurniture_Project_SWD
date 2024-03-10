package com.eFurnitureproject.eFurniture.dtos.chartDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesChartDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private double totalMoney;
}
