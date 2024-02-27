package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.CouponCalculationResponse;
import com.eFurnitureproject.eFurniture.services.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}
