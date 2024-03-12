package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.Address;

import com.eFurnitureproject.eFurniture.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponse {
    private UserResponse userdetail;
    private List<Order> order;
    private Address address;
}
