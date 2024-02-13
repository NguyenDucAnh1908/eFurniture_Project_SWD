package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.Responses.AddressResponse;
import com.eFurnitureproject.eFurniture.dtos.AddressDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAddressService {
    Page<AddressResponse> getAllAddressesByUserId(Long userId, Pageable pageable, String keyword);
    AddressResponse createAddress(AddressDto addressDto, Long userId);
    AddressResponse updateAddress(Long addressId, AddressDto addressDto);
    void deleteAddress(Long addressId);
}
