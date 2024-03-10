package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.models.PaymentStatus;
import com.eFurnitureproject.eFurniture.repositories.PaymentStatusRepository;
import com.eFurnitureproject.eFurniture.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentStatusRepository paymentStatusRepository;
    @Override
    public List<PaymentStatus> getAll() {
        return paymentStatusRepository.findAll();
    }
}
