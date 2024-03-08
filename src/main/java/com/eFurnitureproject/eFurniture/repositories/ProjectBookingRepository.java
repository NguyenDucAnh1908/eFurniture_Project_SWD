package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectBookingRepository extends JpaRepository<ProjectBooking, Long> {
    List<ProjectBooking> findByUserId(Long userId);
    Optional<ProjectBooking> findByCode(String code);
}
