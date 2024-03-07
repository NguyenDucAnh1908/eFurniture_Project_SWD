package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.BookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;

import java.util.List;

public interface IBookingService {

    Design createDesign(Design design);

    Booking getBookingById(Long bookingId);

    BookingDto registerBooking(BookingDto bookingDto) throws DataNotFoundException;

    List<BookingDto> getAllBookingDtos();
}
