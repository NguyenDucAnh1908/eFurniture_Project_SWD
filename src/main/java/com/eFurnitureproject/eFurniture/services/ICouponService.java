package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.CouponResponse;
import com.eFurnitureproject.eFurniture.models.Coupon;

import java.util.List;

public interface ICouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
    List<CouponResponse> getAllCoupon();
}
