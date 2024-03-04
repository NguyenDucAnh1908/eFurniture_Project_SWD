package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
