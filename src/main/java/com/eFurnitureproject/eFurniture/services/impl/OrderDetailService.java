package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.dtos.OrderDetailDto;
import com.eFurnitureproject.eFurniture.dtos.chartDto.SalesChartDTO;
import com.eFurnitureproject.eFurniture.dtos.chartDto.TopSellingProductDTO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        modelMapper.typeMap(OrderDetailDto.class, OrderDetail.class)
                .addMappings(mapper -> mapper.skip(OrderDetail::setId));
        OrderDetail orderDetail = new OrderDetail();
        modelMapper.map(orderDetailDTO, orderDetail);
        orderDetail.setOrders(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDTO)
            throws DataNotFoundException {
        //tìm xem order detail có tồn tại ko đã
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrders())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + orderDetailDTO.getOrders()));
        Product existingProduct = productRepository.findById(orderDetailDTO.getProduct())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.getProduct()));
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setQuantity(orderDetailDTO.getQuantity());
        existingOrderDetail.setTotalAmount(orderDetailDTO.getTotalAmount());
        existingOrderDetail.setDiscount(orderDetailDTO.getDiscount());
        existingOrderDetail.setOrders(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find OrderDetail with id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrdersId(orderId);
    }

    public List<SalesChartDTO> getTotalAmountSoldByDate() {
        List<Object[]> results = orderDetailRepository.getTotalAmountSoldByDate();
        List<SalesChartDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            LocalDate orderDate = (LocalDate) result[0];
            double totalAmount = (double) result[1];
            dtos.add(new SalesChartDTO(orderDate, totalAmount));
        }
        return dtos;
    }

    //    public List<TopSellingProductDTO> findMostSoldProductsToday() {
//        List<Object[]> results = orderDetailRepository.findMostSoldProductsToday();
//        List<TopSellingProductDTO> dtos = new ArrayList<>();
//        for (Object[] result : results) {
//            Product product = (Product) result[0];
//            int totalQuantity = ((Number) result[1]).intValue(); // Convert to int
//            dtos.add(new TopSellingProductDTO(product, totalQuantity));
//        }
//        return dtos;
//    }
    public List<TopSellingProductDTO> findMostSoldProductsByDate() {
        List<Object[]> resultList = orderDetailRepository.findMostSoldProductsByDate();
        return resultList.stream()
                .map(result -> {
                    Product product = (Product) result[0];
                    int totalSold = ((Number) result[1]).intValue();
                    LocalDate date = (LocalDate) result[2];
                    return new TopSellingProductDTO(product, totalSold, date);
                })
                .collect(Collectors.toList());
    }
}
