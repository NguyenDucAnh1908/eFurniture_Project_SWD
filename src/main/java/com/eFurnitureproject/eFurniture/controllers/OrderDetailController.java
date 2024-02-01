package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.components.LocalizationUtils;
import com.eFurnitureproject.eFurniture.dtos.OrderDetailDto;
import com.eFurnitureproject.eFurniture.models.OrderDetail;
import com.eFurnitureproject.eFurniture.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders-detail")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDetailDto orderDetailDto,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderDetail orderDetailResponse = orderDetailService.createOrderDetail(orderDetailDto);
            return ResponseEntity.ok(orderDetailResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
