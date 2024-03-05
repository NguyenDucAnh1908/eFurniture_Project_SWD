package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrdersId(Long orderId);
    @Query("SELECT od.orders.orderDate AS orderDate, SUM(od.totalAmount) AS totalAmount " +
            "FROM OrderDetail od " +
            "GROUP BY od.orders.orderDate")
    List<Object[]> getTotalAmountSoldByDate();

    @Query("SELECT od.product, SUM(od.quantity) AS totalSold, od.orders.orderDate AS date " +
            "FROM OrderDetail od " +
            "GROUP BY od.product, od.orders.orderDate " +
            "ORDER BY totalSold DESC")
    List<Object[]> findMostSoldProductsByDate();

    @Query("SELECT od.product, SUM(od.quantity) AS totalQuantitySold, SUM(od.totalAmount) AS totalAmountSold " +
            "FROM OrderDetail od " +
            "GROUP BY od.product " +
            "ORDER BY totalQuantitySold DESC")
    List<Object[]> findTop5BestSellingProducts();
}
