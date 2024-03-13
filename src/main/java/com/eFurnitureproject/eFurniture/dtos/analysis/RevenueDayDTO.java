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
public class RevenueDayDTO {
    private Double totalSalesToday;
    private Double totalSalesYesterday;
    public String getFormattedTotalSalesToday() {
        DecimalFormat df = new DecimalFormat("#.##");
        if (totalSalesToday == null) {
            return df.format(0);
        } else {
            return df.format(totalSalesToday);
        }
    }

    public String getFormattedTotalSalesYesterday() {
        DecimalFormat df = new DecimalFormat("#.##");
        if (totalSalesYesterday == null) {
            return df.format(0);
        } else {
            return df.format(totalSalesYesterday);
        }
    }

}
