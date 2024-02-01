package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.OrderDetailDto;
import com.eFurnitureproject.eFurniture.models.OrderDetail;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto orderDetailDTO) throws Exception;
}
