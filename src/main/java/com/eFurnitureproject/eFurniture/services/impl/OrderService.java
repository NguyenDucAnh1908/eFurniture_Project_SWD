package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.dtos.CartItemDto;
import com.eFurnitureproject.eFurniture.dtos.OrderDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Order;
import com.eFurnitureproject.eFurniture.models.OrderDetail;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.OrderDetailRepository;
import com.eFurnitureproject.eFurniture.repositories.OrderRepository;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Order createOrder(OrderDto orderDto) throws Exception {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDto.getUserId()));
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mappers -> mappers.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDto, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        //order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDto.getShippingDate() == null
                ? LocalDate.now() : orderDto.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setTotalAmount(orderDto.getTotalAmount());
        orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = null;
        for (CartItemDto cartItemDto : orderDto.getCartItems()) {
            orderDetail = new OrderDetail();
            orderDetail.setOrders(order);
            Long productId = cartItemDto.getProductId();
            int quantity = cartItemDto.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));
            orderDetail.setPrice(product.getPrice());
            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);
            //orderDetail.setDiscount(product.getDiscount());
            orderDetail.setDiscount(orderDetail.getDiscount());

            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
        return order;
    }
    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public Order updateOrder(Long id, OrderDto orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find user with id: " + id));
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        Order selectedOrder = orderRepository.findById(id).orElse(null);
        return selectedOrder;
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
