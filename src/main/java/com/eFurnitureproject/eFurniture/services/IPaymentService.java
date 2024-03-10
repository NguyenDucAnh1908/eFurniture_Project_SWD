package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.models.PaymentStatus;

import java.util.List;

public interface IPaymentService {
    List<PaymentStatus> getAll();
}
