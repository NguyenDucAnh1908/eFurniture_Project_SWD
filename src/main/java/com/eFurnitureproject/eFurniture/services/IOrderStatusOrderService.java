package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.models.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
public interface IOrderStatusOrderService {
    List<OrderStatus> getAll();
}
