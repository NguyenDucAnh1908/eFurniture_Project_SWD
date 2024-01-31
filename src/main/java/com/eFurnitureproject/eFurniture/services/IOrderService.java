package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.OrderDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Order createOrder(OrderDto orderDto) throws Exception;
    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);
    Order updateOrder(Long id, OrderDto orderDTO) throws DataNotFoundException;
}
