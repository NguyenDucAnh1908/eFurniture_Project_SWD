package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.ProjectBookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;

public interface IProjectBookingService {
    ProjectBookingDto createProjectBooking(ProjectBookingDto projectBookingDto) throws DataNotFoundException;
   /* ProjectBooking updateProjectBooking(ProjectBooking projectBooking);*/
    ProjectBooking updateProjectBooking(Long id, ProjectBookingDto projectBookingDto) throws DataNotFoundException;

    ProjectBooking getProjectBookingById(Long projectBookingId);
}
