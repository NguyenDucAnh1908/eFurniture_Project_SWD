package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.dtos.CartItemDto;
import com.eFurnitureproject.eFurniture.dtos.OrderDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.exceptions.InsufficientQuantityException;
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
import org.springframework.web.bind.annotation.CrossOrigin;

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
    @CrossOrigin
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
        if (order.getStatus() == 5) {
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
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);

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
                //orderDetail.setDiscount(product.getDiscount());
                orderDetail.setDiscount(orderDetail.getDiscount());
                if (order.getStatus() == 5) {
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
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword, pageable);
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

}
