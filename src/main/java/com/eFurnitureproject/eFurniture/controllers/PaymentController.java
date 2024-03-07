package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.models.PaymentStatus;
import com.eFurnitureproject.eFurniture.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}/payment-status")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;
    @GetMapping("")
    public ResponseEntity<List<PaymentStatus>> getAll(){
        List<PaymentStatus> paymentStatuses = paymentService.getAll();
        return ResponseEntity.ok(paymentStatuses);
    }
}
