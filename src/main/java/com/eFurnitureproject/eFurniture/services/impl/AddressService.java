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
                .ward(addressDto.getWard())
                .district(addressDto.getDistrict())
                .province(addressDto.getProvince())
                .phoneNumber(addressDto.getPhoneNumber())
                .user(userid)
                .build();

        return addressRepository.save(newAddress);

    }

    @Override
    public Address updateAddress(Long addressId, AddressDto addressDto) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));

        if (addressDto.getFirstName() != null && !addressDto.getFirstName().isEmpty()) {
            existingAddress.setFirstName(addressDto.getFirstName());
        }
        if (addressDto.getLastName() != null && !addressDto.getLastName().isEmpty()) {
            existingAddress.setLastName(addressDto.getLastName());
        }
        if (addressDto.getStreetAddress() != null && !addressDto.getStreetAddress().isEmpty()) {
            existingAddress.setStreetAddress(addressDto.getStreetAddress());
        }
        if (addressDto.getProvince() != null && !addressDto.getProvince().isEmpty()) {
            existingAddress.setProvince(addressDto.getProvince());
        }

        if (addressDto.getPhoneNumber() != null && !addressDto.getPhoneNumber().isEmpty()) {
            existingAddress.setPhoneNumber(addressDto.getPhoneNumber());
        }


        return addressRepository.save(existingAddress);
    }



    @Override
    public void deleteAddress(Long addressId) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));

        addressRepository.delete(existingAddress);
    }
}
