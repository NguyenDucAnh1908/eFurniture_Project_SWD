package com.eFurnitureproject.eFurniture.dtos;

import com.eFurnitureproject.eFurniture.models.StatusDelivery;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryDto {
    private Long orderId;
    private StatusDelivery deliveryStatus;
    private String trackingNumber;
}
