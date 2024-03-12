package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.BookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    private static UserRepository userRepository;

    @Autowired
    public BookingConverter(UserRepository userRepository) {
        BookingConverter.userRepository = userRepository;
    }


    public static BookingDto toDTO(Booking booking) {
        BookingDto.BookingDtoBuilder builder = BookingDto.builder()
                .id(booking.getId())
                .firstName(booking.getFirstName())
                .lastName(booking.getLastName())
                .phoneNumber(booking.getPhoneNumber())
                .streetAddress(booking.getStreetAddress())
                .wardCode(booking.getWardCode())
                .districtCode(booking.getDistrictCode())
                .provinceCode(booking.getProvinceCode())
                .wardName(booking.getWardName())
                .districtName(booking.getDistrictName())
                .provinceName(booking.getProvinceName())
                .status(booking.getStatus())
                .schedule(booking.getSchedule())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .note(booking.getNote());

        if (booking.getUser() != null) {
            builder.userId(booking.getUser().getId());
        }
        if (booking.getDesigner() != null) {
            builder.designerId(booking.getDesigner().getId());
        }

        return builder.build();
    }

    public static Booking toEntity(BookingDto bookingDto) throws DataNotFoundException {
        return Booking.builder()
                .id(bookingDto.getId())
                .firstName(bookingDto.getFirstName())
                .lastName(bookingDto.getLastName())
                .phoneNumber(bookingDto.getPhoneNumber())
                .streetAddress(bookingDto.getStreetAddress())
                .wardCode(bookingDto.getWardCode())
                .districtCode(bookingDto.getDistrictCode())
                .provinceCode(bookingDto.getProvinceCode())
                .wardName(bookingDto.getWardName())
                .districtName(bookingDto.getDistrictName())
                .schedule(bookingDto.getSchedule())
                .provinceName(bookingDto.getProvinceName())
                .status("Unconfirmed")
                .note(bookingDto.getNote())
                .user(userRepository.findById(bookingDto.getUserId())
                        .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + bookingDto.getUserId())))
                .build();
    }
}
