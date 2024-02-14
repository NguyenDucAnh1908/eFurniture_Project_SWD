package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.AddressResponse;
import com.eFurnitureproject.eFurniture.converter.AddressConverter;
import com.eFurnitureproject.eFurniture.dtos.AddressDto;
import com.eFurnitureproject.eFurniture.models.Address;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.AddressRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IAddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService  {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;



    @Override
    public Page<AddressResponse> getAllAddressesByUserId(Long userId, Pageable pageable, String keyword) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        return addressRepository.searchAddress(keyword, pageable, userId)
                .map(AddressConverter::toResponse);
    }

    @Override
    public Address createAddress(AddressDto addressDto, Long userId) {

        User userid = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));


        Address newAddress = Address.builder()
                .firstName(addressDto.getFirstName())
                .lastName(addressDto.getLastName())
                .streetAddress(addressDto.getStreetAddress())
                .country(addressDto.getCountry())
                .province(addressDto.getProvince())
                .phoneNumber(addressDto.getPhoneNumber())
                .postalCode(addressDto.getPostalCode())
                .user(userid)
                .build();

        return addressRepository.save(newAddress);

    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressDto addressDto) {
        return null;
    }

    @Override
    public void deleteAddress(Long addressId) {

    }
}
