package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.BookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookingService {
    ProjectBooking createProjectBooking(ProjectBooking projectBooking);
    Design createDesign(Design design);

    Booking getBookingById(Long bookingId);

    BookingDto registerBooking(BookingDto bookingDto) throws DataNotFoundException;

    Page<BookingDto> getAllBookingDtos(Pageable pageable);
}
