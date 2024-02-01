package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.dtos.OrderDetailDto;
import com.eFurnitureproject.eFurniture.dtos.OrderDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Order;
import com.eFurnitureproject.eFurniture.models.OrderDetail;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.repositories.OrderDetailRepository;
import com.eFurnitureproject.eFurniture.repositories.OrderRepository;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.services.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDto orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrders())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find Order with id : " + orderDetailDTO.getOrders()));
        Product product = productRepository.findById(orderDetailDTO.getProduct())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.getProduct()));
        modelMapper.typeMap(OrderDto.class, OrderDetail.class)
                .addMappings(mapper -> mapper.skip(OrderDetail::setId));
        OrderDetail orderDetail = new OrderDetail();
        modelMapper.map(orderDetailDTO, orderDetail);
        orderDetail.setOrders(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }
}
