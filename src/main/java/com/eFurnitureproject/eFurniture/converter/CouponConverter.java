package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.Responses.CouponResponse;
import com.eFurnitureproject.eFurniture.models.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponConverter {
    public static CouponResponse fromCoupon(Coupon coupon) {
        CouponResponse couponResponse = CouponResponse
                .builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .active(coupon.isActive())
                .couponCondition(coupon.getCouponCondition())
                .build();
        return couponResponse;
    }
}
