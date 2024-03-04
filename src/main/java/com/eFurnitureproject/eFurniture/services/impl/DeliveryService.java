package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.DeliveryConverter;
import com.eFurnitureproject.eFurniture.dtos.DeliveryDto;
import com.eFurnitureproject.eFurniture.models.Delivery;
import com.eFurnitureproject.eFurniture.models.Order;
import com.eFurnitureproject.eFurniture.models.Enum.StatusDelivery;
import com.eFurnitureproject.eFurniture.repositories.DeliveryRepository;

import com.eFurnitureproject.eFurniture.services.IDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService implements IDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderService orderService;
    private final DeliveryConverter deliveryConverter;

    @Transactional
    public void updateDeliveryStatus(DeliveryDto deliveryDto) {
        Order order = orderService.getOrder(deliveryDto.getOrderId());

        Delivery delivery = deliveryRepository.findByOrderId(deliveryDto.getOrderId())
                .orElse(new Delivery());

        delivery.setOrder(order);
        delivery.setDeliveryStatus(deliveryDto.getDeliveryStatus());
        delivery.setTrackingNumber(deliveryDto.getTrackingNumber());

        deliveryRepository.save(delivery);
    }

    @Transactional(readOnly = true)
    public DeliveryDto getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .map(deliveryConverter::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<DeliveryDto> getDeliveriesByStatus(StatusDelivery status) {
        List<Delivery> deliveries = deliveryRepository.findByDeliveryStatus(status);
        return deliveries.stream()
                .map(deliveryConverter::toDTO)
                .collect(Collectors.toList());
    }

}