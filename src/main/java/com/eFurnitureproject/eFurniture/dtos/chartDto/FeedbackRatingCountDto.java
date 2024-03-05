package com.eFurnitureproject.eFurniture.dtos.chartDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRatingCountDto {
    private int rating;
    private long count;
}
