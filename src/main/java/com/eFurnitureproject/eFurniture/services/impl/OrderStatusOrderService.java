package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.models.OrderStatus;
import com.eFurnitureproject.eFurniture.repositories.OrderStatusRepository;
import com.eFurnitureproject.eFurniture.services.IOrderStatusOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderStatusOrderService implements IOrderStatusOrderService {
    private final OrderStatusRepository orderStatusRepository;
    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepository.findAll();
    }
}
