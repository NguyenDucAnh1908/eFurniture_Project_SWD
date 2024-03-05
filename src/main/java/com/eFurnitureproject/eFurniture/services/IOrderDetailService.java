package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.OrderDetailDto;
import com.eFurnitureproject.eFurniture.dtos.chartDto.TopSellingProductDTO;
import com.eFurnitureproject.eFurniture.dtos.chartDto.SalesChartDTO;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.OrderDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto orderDetailDTO) throws Exception;
    OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDTO)
            throws DataNotFoundException;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
    List<SalesChartDTO> getTotalAmountSoldByDate();
    List<TopSellingProductDTO> findMostSoldProductsByDate();
}
