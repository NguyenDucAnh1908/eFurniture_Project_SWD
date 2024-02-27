package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.CouponCalculationResponse;
import com.eFurnitureproject.eFurniture.Responses.CouponResponse;
import com.eFurnitureproject.eFurniture.models.Coupon;
import com.eFurnitureproject.eFurniture.services.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}/coupons")
//@Validated
//Dependency Injection
@RequiredArgsConstructor
public class CouponController {
    private final ICouponService couponService;
    @GetMapping("/calculate")
    public ResponseEntity<CouponCalculationResponse> calculateCouponValue(
            @RequestParam("couponCode") String couponCode,
            @RequestParam("totalAmount") double totalAmount) {
        try {
            double finalAmount = couponService.calculateCouponValue(couponCode, totalAmount);
            CouponCalculationResponse response = CouponCalculationResponse.builder()
                    .result(finalAmount)
                    .errorMessage("")
                    .build();
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    CouponCalculationResponse.builder()
                            .result(totalAmount)
                            .errorMessage(e.getMessage())
                            .build());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CouponResponse>> getAllCoupon(){
        List<CouponResponse> coupons = couponService.getAllCoupon();
        return ResponseEntity.ok(coupons);
    }
}
