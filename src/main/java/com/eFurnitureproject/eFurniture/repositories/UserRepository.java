package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
