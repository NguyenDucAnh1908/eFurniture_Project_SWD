package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.DeliveryDto;
import com.eFurnitureproject.eFurniture.models.Delivery;
import org.springframework.stereotype.Component;

@Component

public class DeliveryConverter {
    public DeliveryDto toDTO(Delivery delivery) {
        return DeliveryDto.builder()
                .orderId(delivery.getOrder().getId())
                .deliveryStatus(delivery.getDeliveryStatus())
                .trackingNumber(delivery.getTrackingNumber())
                .build();
    }
}
