package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.models.ProjectBooking;

public interface IProjectBookingService {
    ProjectBooking updateProjectBooking(ProjectBooking projectBooking);


    ProjectBooking getProjectBookingById(Long projectBookingId);
}
