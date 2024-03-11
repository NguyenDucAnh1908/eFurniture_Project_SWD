package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Enum.Role;
import com.eFurnitureproject.eFurniture.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u " +
            "WHERE YEAR(u.createdAt) = YEAR(CURRENT_DATE) AND MONTH(u.createdAt) = MONTH(CURRENT_DATE)")
    Long countUsersThisMonth();

    @Query("SELECT COUNT(u) FROM User u " +
            "WHERE YEAR(u.createdAt) = YEAR(CURRENT_DATE) AND MONTH(u.createdAt) = MONTH(CURRENT_DATE) - 1")
    Long countUsersLastMonth();

    Page<User> findByRole(Role role, PageRequest pageRequest);
}
