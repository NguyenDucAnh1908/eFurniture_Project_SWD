package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrdersId(Long orderId);
}
