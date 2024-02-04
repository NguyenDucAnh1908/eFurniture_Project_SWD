package com.eFurnitureproject.eFurniture.services;

public interface ICouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
}
