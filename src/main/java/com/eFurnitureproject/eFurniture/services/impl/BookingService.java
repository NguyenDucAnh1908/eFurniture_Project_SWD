package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.BookingConverter;
import com.eFurnitureproject.eFurniture.dtos.BookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.BookingRepository;
import com.eFurnitureproject.eFurniture.repositories.DesignRepository;
import com.eFurnitureproject.eFurniture.repositories.ProjectBookingRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IBookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;
    private final DesignRepository designRepository;
    private final ProjectBookingRepository projectBookingRepository;
    private final UserRepository userRepository;


    public BookingDto registerBooking(BookingDto bookingDto) throws DataNotFoundException {
        try {
            User user = userRepository.findById(bookingDto.getUserId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find user with id: " + bookingDto.getUserId()));
            Booking booking = BookingConverter.toEntity(bookingDto);
            booking.setUser(user);

            booking = bookingRepository.save(booking);
            return BookingConverter.toDTO(booking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error register booking: " + e.getMessage());
        }
    }

    @Override
    public Design createDesign(Design design) {
        return designRepository.save(design);
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        return bookingOptional.orElse(null);
    }

    @Override
    public Page<BookingDto> getAllBookingDtos(Pageable pageable) {
        Page<Booking> bookingsPage = bookingRepository.findAll(pageable);
        return bookingsPage.map(BookingConverter::toDTO);
    }

    @Override
    public Page<BookingDto> getAllBookingDtosByUserId(Pageable pageable, Long userId) {
        Page<Booking> bookingsPage = bookingRepository.findAllByUserId(pageable, userId);
        return bookingsPage.map(BookingConverter::toDTO);
    }


}
