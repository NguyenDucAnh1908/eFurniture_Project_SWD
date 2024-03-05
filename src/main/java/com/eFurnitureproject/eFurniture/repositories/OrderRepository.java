package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    @Query("SELECT o FROM Order o WHERE o.active = true AND (:keyword IS NULL OR :keyword = '' OR " +
            "o.fullName LIKE %:keyword% " +
            "OR o.address LIKE %:keyword% " +
            "OR o.notes LIKE %:keyword% " +
            "OR o.email LIKE %:keyword%)")
    Page<Order> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Order o")
    long countTotalOrders();

    @Query("SELECT COUNT(o) FROM Order o " +
            "WHERE FUNCTION('YEAR', o.orderDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND FUNCTION('MONTH', o.orderDate) = FUNCTION('MONTH', CURRENT_DATE)")
    long countOrdersThisMonth();

    @Query("SELECT COUNT(o) FROM Order o " +
            "WHERE FUNCTION('YEAR', o.orderDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND FUNCTION('MONTH', o.orderDate) = FUNCTION('MONTH', CURRENT_DATE) - 1")
    long countOrdersLastMonth();

    default double calculateOrderChange() {
        long currentMonthCount = countOrdersThisMonth();
        long lastMonthCount = countOrdersLastMonth();

        if (lastMonthCount == 0) {
            return 0; // To avoid division by zero
        }

        double percentageChange = ((double) (currentMonthCount - lastMonthCount) / lastMonthCount) * 100;
        return percentageChange;
    }

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE MONTH(o.orderDate) = MONTH(CURRENT_DATE())")
    Double findTotalRevenueCurrentMonth();

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE MONTH(o.orderDate) = MONTH(CURRENT_DATE()) - 1")
    Double findTotalRevenueLastMonth();

    @Query(value = "SELECT SUM(o.total_amount) " +
            "FROM orders o " +
            "WHERE o.order_date BETWEEN DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '1 week' AND DATE_TRUNC('week', CURRENT_DATE)",
            nativeQuery = true)
    Double getTotalRevenueLastWeek();

    @Query("SELECT SUM(o.totalAmount) " +
            "FROM Order o " +
            "WHERE o.orderDate BETWEEN DATE_TRUNC('week', CURRENT_DATE) AND CURRENT_DATE")
    Double getTotalRevenueCurrentWeek();
}//o.active = true AND
