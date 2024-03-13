package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.Responses.AddressResponse;
import com.eFurnitureproject.eFurniture.Responses.UserResponse;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService  {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;



    @Override
    public Page<AddressResponse> getAllAddressesByUserId(Long userId, Pageable pageable, String keyword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Page<Address> addressPage = addressRepository.searchAddress(keyword, pageable, userId);

        Page<AddressResponse> addressResponsePage = addressPage.map(address -> {
            AddressResponse addressResponse = AddressConverter.toResponse(address);

            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .phoneNumber(user.getPhoneNumber())
                    .address(user.getAddress())

                    .active(user.isActive())
                    .dateOfBirth(user.getDateOfBirth())
                    .facebookAccountId(user.getFacebookAccountId())
                    .googleAccountId(user.getGoogleAccountId())
                    .build();

            addressResponse.setUser(userResponse);

            return addressResponse;
        });

        return addressResponsePage;
    }



    @Override
    public Address createAddress(AddressDto addressDto, Long userId) {

        User userid = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));


        Address newAddress = Address.builder()
                .firstName(addressDto.getFirstName())
                .lastName(addressDto.getLastName())
                .streetAddress(addressDto.getStreetAddress())
                .wardCode(addressDto.getWardCode())
                .districtCode(addressDto.getDistrictCode())
                .provinceCode(addressDto.getProvinceCode())
                .wardName(addressDto.getWardName())
                .districtName(addressDto.getDistrictName())
                .provinceName(addressDto.getProvinceName())
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

    @Override
    public Address getAddressById(Long addressId) throws Exception {
        Optional<Address> addressOptional = addressRepository.findById(addressId);

        if (addressOptional.isPresent()) {
            return addressOptional.get();
        } else {
            throw new Exception("Can not find Blog with id= " + addressId);
        }
    }
}
