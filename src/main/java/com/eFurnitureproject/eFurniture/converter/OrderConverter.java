package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.Responses.OrderResponse;
import com.eFurnitureproject.eFurniture.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public static OrderResponse fromOrder(Order order) {
        OrderResponse orderResponse =  OrderResponse
                .builder()
                .id(order.getId())
                .userId(order.getId())
                .active(order.getActive())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .email(order.getEmail())
                .fullName(order.getFullName())
                .discounts(order.getDiscounts())
                .notes(order.getNotes())
                .shippingDate(order.getShippingDate())
                .shippingMethod(order.getShippingMethod())
                .trackingNumber(order.getTrackingNumber())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .orderDetails(order.getOrderDetails())
                .build();
        return orderResponse;
    }
}
