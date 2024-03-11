package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Design;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DesignRepository extends JpaRepository<Design, Long> {
    List<Design> findByProjectBookingId(Long projectId);
    Optional<Design> findTopByOrderByCodeDesignDesc();
}
