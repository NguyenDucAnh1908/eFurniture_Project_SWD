package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.Responses.AddressResponse;
import com.eFurnitureproject.eFurniture.dtos.AddressDto;
import com.eFurnitureproject.eFurniture.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    public static AddressDto toDto(Address entity) {
        AddressDto dto = new AddressDto();
        dto.setId(entity.getId());
        dto.setStreetAddress(entity.getStreetAddress());
//        dto.setProvince(entity.getProvinceCode());
//        dto.setCountry(entity.getDistrict());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }


    public static Address toEntity(AddressDto dto) {
        Address entity = new Address();
        entity.setStreetAddress(dto.getStreetAddress());
//        entity.setProvinceCode(dto.getProvince());
//        entity.setDistrict(dto.getCountry());
        entity.setPhoneNumber(dto.getPhoneNumber());
        // entity.setUser(userRepository.findById(dto.getUserAddressId()).orElse(null));
        return entity;
    }


    public static AddressResponse toResponse(Address address) {
        AddressResponse addressResponse = AddressResponse.builder()
                .id(address.getId())
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .streetAddress(address.getStreetAddress())
                .wardCode(address.getWardCode())
                .districtCode(address.getDistrictCode())
                .provinceCode(address.getProvinceCode())
                .wardName(address.getWardName())
                .districtName(address.getDistrictName())
                .provinceName(address.getProvinceName())
                .phoneNumber(address.getPhoneNumber())
//                .userAddressId(address.getUser())
                .build();
        return addressResponse;
    }
}
