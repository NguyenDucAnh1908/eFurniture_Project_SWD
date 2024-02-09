package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.DeliveryDto;
import com.eFurnitureproject.eFurniture.models.StatusDelivery;

import java.util.List;

public interface IDeliveryService {
    void updateDeliveryStatus(DeliveryDto deliveryDto);
    DeliveryDto getDeliveryByOrderId(Long orderId);
    List<DeliveryDto> getDeliveriesByStatus(StatusDelivery status);
}
