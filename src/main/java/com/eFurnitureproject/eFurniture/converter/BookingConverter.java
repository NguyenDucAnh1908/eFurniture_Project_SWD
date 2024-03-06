package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.BookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    private static UserRepository userRepository;  // Add UserRepository

    @Autowired
    public BookingConverter(UserRepository userRepository) {
        BookingConverter.userRepository = userRepository;
    }

    public static BookingDto toDTO(Booking booking) {
        BookingDto.BookingDtoBuilder builder = BookingDto.builder()
                .streetAddress(booking.getStreetAddress())
                .wardCode(booking.getWardCode())
                .districtCode(booking.getDistrictCode())
                .provinceCode(booking.getProvinceCode())
                .wardName(booking.getWardName())
                .districtName(booking.getDistrictName())
                .provinceName(booking.getProvinceName())
                .status(booking.getStatus())
                .note(booking.getNote())
                .userId(booking.getUser().getId());
        if (booking.getUser() != null) {
            builder.userId(booking.getUser().getId());
        }

        return builder.build();
    }

    public static Booking toEntity(BookingDto bookingDto) throws DataNotFoundException {
        return Booking.builder()
                .streetAddress(bookingDto.getStreetAddress())
                .wardCode(bookingDto.getWardCode())
                .districtCode(bookingDto.getDistrictCode())
                .provinceCode(bookingDto.getProvinceCode())
                .wardName(bookingDto.getWardName())
                .districtName(bookingDto.getDistrictName())
                .provinceName(bookingDto.getProvinceName())
                .status(bookingDto.getStatus())
                .note(bookingDto.getNote())
                .user(userRepository.findById(bookingDto.getUserId())
                        .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + bookingDto.getUserId())))
                .build();
    }
}
