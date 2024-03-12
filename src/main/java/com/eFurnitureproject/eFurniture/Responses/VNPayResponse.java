package com.eFurnitureproject.eFurniture.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VNPayResponse {
    private String status;
    private String message;
    private String URL;
}
