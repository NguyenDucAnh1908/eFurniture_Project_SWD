package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.repositories.BookingRepository;
import com.eFurnitureproject.eFurniture.repositories.DesignRepository;
import com.eFurnitureproject.eFurniture.repositories.ProjectBookingRepository;
import com.eFurnitureproject.eFurniture.services.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;
    private final DesignRepository designRepository;
    private final ProjectBookingRepository projectBookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public ProjectBooking createProjectBooking(ProjectBooking projectBooking) {
        return projectBookingRepository.save(projectBooking);
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
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
