package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.ProjectBookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;

import java.util.List;

public interface IProjectBookingService {
    ProjectBookingDto createProjectBooking(ProjectBookingDto projectBookingDto) throws DataNotFoundException;
   /* ProjectBooking updateProjectBooking(ProjectBooking projectBooking);*/
    ProjectBooking updateProjectBooking(Long id, ProjectBookingDto projectBookingDto) throws DataNotFoundException;
    ProjectBookingDto getProjectBookingById(Long projectBookingId);
//    ProjectBooking getProjectBookingById(Long projectBookingId);
    List<ProjectBooking> getProjectBookingsByUserId(Long userId);
    ProjectBookingDto getProjectBookingByCode(String code) throws DataNotFoundException;
    ProjectBookingDto getProjectBookingByBookingId(String BookingId);
    List<ProjectBookingDto> getAllProjectBookings();
    ProjectBookingDto getProjectBookingByBookingId(Long bookingId) throws DataNotFoundException;
}
