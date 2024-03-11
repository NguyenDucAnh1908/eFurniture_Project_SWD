package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.dtos.CartItemDto;
import com.eFurnitureproject.eFurniture.dtos.OrderDto;
import com.eFurnitureproject.eFurniture.dtos.analysis.OrderStatsDTO;
import com.eFurnitureproject.eFurniture.dtos.analysis.RevenueDTO;
import com.eFurnitureproject.eFurniture.dtos.analysis.RevenueDayDTO;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.exceptions.InsufficientQuantityException;
import com.eFurnitureproject.eFurniture.models.*;
import com.eFurnitureproject.eFurniture.repositories.*;
import com.eFurnitureproject.eFurniture.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final  PaymentStatusRepository paymentStatusRepository;
    private final ModelMapper modelMapper;
    @CrossOrigin
    @Transactional
    public Order createOrder(OrderDto orderDto) throws Exception {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDto.getUserId()));
        OrderStatus orderStatus = orderStatusRepository.findById(orderDto.getOrderStatus())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order status with id: " + orderDto.getUserId()));
        PaymentStatus paymentStatus = paymentStatusRepository.findById(orderDto.getPaymentStatus())
                .orElseThrow(() -> new DataNotFoundException("Cannot find payment status with id: " + orderDto.getUserId()));
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mappers -> mappers.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDto, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(orderStatus);
        order.setPaymentStatus(paymentStatus);
        LocalDate shippingDate = orderDto.getShippingDate() == null
                ? LocalDate.now() : orderDto.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setTotalAmount(orderDto.getTotalAmount());
        if (order.getOrderStatus() != null && order.getOrderStatus().getId() == 5) {
            updateProductQuantities(orderDto.getCartItems());
        }
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
    @Transactional
    public Order updateOrder(Long id, OrderDto orderDTO) throws DataNotFoundException {
        //try {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find user with id: " + id));
//        OrderStatus orderStatus = orderStatusRepository.findById(orderDTO.getOrderStatus())
//                .orElseThrow(() -> new DataNotFoundException("Cannot find order status with id: " + orderDTO.getUserId()));
//        PaymentStatus paymentStatus = paymentStatusRepository.findById(orderDTO.getPaymentStatus())
//                .orElseThrow(() -> new DataNotFoundException("Cannot find payment status with id: " + orderDTO.getUserId()));
        OrderStatus orderStatus = orderStatusRepository.findById(orderDTO.getOrderStatus())
                .orElse(order.getOrderStatus());

        PaymentStatus paymentStatus = paymentStatusRepository.findById(orderDTO.getPaymentStatus())
                .orElse(order.getPaymentStatus());
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        order.setOrderStatus(orderStatus);
        order.setPaymentStatus(paymentStatus);
            orderRepository.save(order);
            List<OrderDetail> orderDetails = new ArrayList<>();
            OrderDetail orderDetail = null;
            for (CartItemDto cartItemDto : orderDTO.getCartItems()) {
                orderDetail = new OrderDetail();
                orderDetail.setOrders(order);
                Long productId = cartItemDto.getProductId();
                int quantity = cartItemDto.getQuantity();
                OrderDetail orderDetailExisting = orderDetailRepository.findById(cartItemDto.getId())
                        .orElseThrow(() -> new DataNotFoundException("Orderdetail not found with id: " + cartItemDto.getId()));
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));
                orderDetail.setId(orderDetailExisting.getId());
                orderDetail.setPrice(product.getPrice());
                orderDetail.setProduct(product);
                orderDetail.setQuantity(quantity);
                order.setOrderStatus(orderStatus);
                order.setPaymentStatus(paymentStatus);
                //orderDetail.setDiscount(product.getDiscount());
                orderDetail.setDiscount(orderDetail.getDiscount());
                if (order.getOrderStatus() != null && order.getOrderStatus().getId() == 5) {
                    updateProductQuantities(orderDTO.getCartItems());
                }
                orderDetails.add(orderDetail);
            }
            orderDetailRepository.saveAll(orderDetails);
            return order;
//        } catch (Exception e) {
//            throw new DataNotFoundException("error");
//        }
    }

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Long paymentStatusId,Pageable pageable) {
        return orderRepository.findByKeyword(keyword,paymentStatusId, pageable);
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
    private void updateProductQuantities(List<CartItemDto> cartItems) throws DataNotFoundException {
        for (CartItemDto cartItemDto : cartItems) {
            Long productId = cartItemDto.getProductId();
            int quantity = cartItemDto.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));

            // Ensure that the quantity to be deducted is not greater than the available quantity
            if (quantity > product.getQuantity()) {
                throw new InsufficientQuantityException("Not enough quantity available for product with id: " + productId);
            }

            int updatedQuantity = product.getQuantity() - quantity;
            product.setQuantity(updatedQuantity);
            productRepository.save(product);
        }
    }

    public OrderStatsDTO getOrderStats() {
        OrderStatsDTO orderStatsDTO = new OrderStatsDTO();
        long totalOrders = orderRepository.countTotalOrders();
        orderStatsDTO.setTotalOrders(totalOrders);
        double orderChangePercentage = orderRepository.calculateOrderChange();
        orderStatsDTO.setOrderChangePercentage(orderChangePercentage);
        return orderStatsDTO;
    }

    public RevenueDTO getRevenueStatistics() {
        Double currentMonthRevenue = orderRepository.findTotalRevenueCurrentMonth();
        Double lastMonthRevenue = orderRepository.findTotalRevenueLastMonth();

        Double revenueChangePercentage = calculateRevenueChangePercentage(currentMonthRevenue, lastMonthRevenue);

        return new RevenueDTO(currentMonthRevenue, lastMonthRevenue, revenueChangePercentage);
    }

    private Double calculateRevenueChangePercentage(Double currentMonthRevenue, Double lastMonthRevenue) {
        if (lastMonthRevenue == null || lastMonthRevenue == 0) {
            return currentMonthRevenue == null ? null : 100.0; // Assuming a 100% increase if last month revenue is 0
        }
        return ((currentMonthRevenue - lastMonthRevenue) / lastMonthRevenue) * 100;
    }

    public RevenueDayDTO getTotalSales() {
        RevenueDayDTO totalSalesDTO = new RevenueDayDTO();
        totalSalesDTO.setTotalSalesToday(orderRepository.getTotalAmountToday());
        totalSalesDTO.setTotalSalesYesterday(orderRepository.getTotalAmountYesterday());
        return totalSalesDTO;
    }

    @Override
    public Integer countOrdersByProductId(Long productId) {
        return orderDetailRepository.countOrderByProductId(productId);
    }
    @Override
    public Optional<Double> getProductRevenue(Long productId) {
        return orderDetailRepository.findTotalRevenueByProductId(productId);
    }
}
