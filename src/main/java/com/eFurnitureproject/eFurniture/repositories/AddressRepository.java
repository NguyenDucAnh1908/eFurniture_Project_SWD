package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Address;
import com.eFurnitureproject.eFurniture.models.Blog;
import com.eFurnitureproject.eFurniture.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByUser_Id(Long userId, Pageable pageable);

    @Query("select b FROM Address b " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR b.streetAddress LIKE %:keyword% OR b.provinceCode LIKE %:keyword%) " +
            "AND (:user_address IS NULL OR :user_address = 0 OR b.user.id = : user_address) ")
    Page<Address> searchAddress(
            @Param("keyword") String keyword, Pageable pageable,
            @Param("user_address") Long user_address);
}
