package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectBookingRepository extends JpaRepository<ProjectBooking, Long> {
    ProjectBooking findByCode(String code);
    List<ProjectBooking> findByUserId(Long userId);
    ProjectBooking findByBookingId(Long bookingId);

}
