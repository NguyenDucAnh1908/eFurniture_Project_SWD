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
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setAddressType(entity.getAddressType());
        dto.setDefaultAddress(entity.getDefaultAddress());
//        dto.setUserAddressId(entity.getUser().getId());
        return dto;
    }


    public static Address toEntity(AddressDto dto) {
        Address entity = new Address();
        entity.setStreetAddress(dto.getStreetAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setAddressType(dto.getAddressType());
        entity.setDefaultAddress(dto.getDefaultAddress());
        // Trong thực tế, bạn sẽ cần thêm logic để lấy và thiết lập đối tượng User dựa trên ID từ DTO.
        // entity.setUser(userRepository.findById(dto.getUserAddressId()).orElse(null));
        return entity;
    }


    public static AddressResponse toResponse(Address address) {
        AddressResponse addressResponse = AddressResponse.builder()
                .id(address.getId())
                .streetAddress(address.getStreetAddress())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .phoneNumber(address.getPhoneNumber())
                .email(address.getEmail())
                .addressType(address.getAddressType())
                .defaultAddress(address.getDefaultAddress())
//                .userAddressId(address.getUser().getAddress())
                .build();
        return addressResponse;
    }
}
