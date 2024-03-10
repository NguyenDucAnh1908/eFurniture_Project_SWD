package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
