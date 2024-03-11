package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
}
