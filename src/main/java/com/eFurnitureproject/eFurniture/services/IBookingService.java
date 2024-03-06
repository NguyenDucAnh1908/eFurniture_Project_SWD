package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;

import java.util.List;

public interface IBookingService {
    Booking createBooking(Booking booking);
    ProjectBooking createProjectBooking(ProjectBooking projectBooking);
    Design createDesign(Design design);

    Booking getBookingById(Long bookingId);

    List<Booking> getAllBookings();

}
