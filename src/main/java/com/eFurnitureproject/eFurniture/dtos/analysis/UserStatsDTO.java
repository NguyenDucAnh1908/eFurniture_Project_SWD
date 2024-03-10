package com.eFurnitureproject.eFurniture.dtos.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {
    private Long usersThisMonth;
    private Long usersLastMonth;
    private double percentageChange;
    public String getFormattedPercentageChange() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(percentageChange);
    }
}
