package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
