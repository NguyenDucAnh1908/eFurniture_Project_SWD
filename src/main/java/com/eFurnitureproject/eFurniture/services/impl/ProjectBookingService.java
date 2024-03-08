package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.BookingConverter;
import com.eFurnitureproject.eFurniture.converter.ProjectBookingConverter;
import com.eFurnitureproject.eFurniture.dtos.ProjectBookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.BookingRepository;
import com.eFurnitureproject.eFurniture.repositories.ProjectBookingRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IProjectBookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProjectBookingService implements IProjectBookingService {
    private final ProjectBookingRepository projectBookingRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    @Override
    public ProjectBookingDto createProjectBooking(ProjectBookingDto projectBookingDto) {
        try {
            User user = userRepository.findById(projectBookingDto.getUserId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find user with id: " + projectBookingDto.getUserId()));
            Booking booking = bookingRepository.findById(projectBookingDto.getBookingId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find booking with id: " + projectBookingDto.getBookingId()));

            ProjectBooking projectBooking = ProjectBookingConverter.toEntity(projectBookingDto, userRepository, bookingRepository);
            projectBooking.setUser(user);
            projectBooking.setBooking(booking);

            projectBooking = projectBookingRepository.save(projectBooking);
            return ProjectBookingConverter.toDTO(projectBooking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error registering project booking: " + e.getMessage());
        }
    }

   /* @Override
    public ProjectBooking updateProjectBooking(ProjectBooking projectBooking) {
        // Kiểm tra xem có tồn tại ProjectBooking với ID đã cho hay không
        Optional<ProjectBooking> existingProjectBooking = projectBookingRepository.findById(projectBooking.getId());
        if (existingProjectBooking.isPresent()) {
            // Nếu tồn tại, cập nhật các trường của đối tượng hiện có bằng dữ liệu mới
            ProjectBooking updatedProjectBooking = existingProjectBooking.get();

            // Ví dụ cập nhật một số trường, bạn có thể thêm các trường khác tương tự
            updatedProjectBooking.setProjectName(projectBooking.getProjectName());
            updatedProjectBooking.setProjectType(projectBooking.getProjectType());
            updatedProjectBooking.setSize(projectBooking.getSize());
            updatedProjectBooking.setDesignStyle(projectBooking.getDesignStyle());
            updatedProjectBooking.setColorSchemes(projectBooking.getColorSchemes());
            updatedProjectBooking.setIntendUse(projectBooking.getIntendUse());
            updatedProjectBooking.setOccupantsNumber(projectBooking.getOccupantsNumber());
            updatedProjectBooking.setTimeLine(projectBooking.getTimeLine());
            updatedProjectBooking.setProjectPrice(projectBooking.getProjectPrice());
            // Đảm bảo cập nhật tất cả các trường cần thiết theo mô hình của bạn

            // Lưu lại đối tượng đã cập nhật
            return projectBookingRepository.save(updatedProjectBooking);
        } else {

            throw new EntityNotFoundException("ProjectBooking with ID " + projectBooking.getId() + " not found.");
        }
    }*/ public ProjectBooking updateProjectBooking(Long id, ProjectBookingDto projectBookingDto) throws DataNotFoundException {
       // Kiểm tra xem có tồn tại ProjectBooking với ID đã cho hay không
       ProjectBooking existingProjectBooking = projectBookingRepository.findById(id)
               .orElseThrow(() -> new DataNotFoundException("ProjectBooking with id " + id + " not found"));

       // Cập nhật thông tin của ProjectBooking với dữ liệu từ projectBookingDto
       existingProjectBooking.setProjectName(projectBookingDto.getProjectName());
       existingProjectBooking.setProjectType(projectBookingDto.getProjectType());
       existingProjectBooking.setSize(projectBookingDto.getSize());
       existingProjectBooking.setDesignStyle(projectBookingDto.getDesignStyle());
       existingProjectBooking.setColorSchemes(projectBookingDto.getColorSchemes());
       existingProjectBooking.setIntendUse(projectBookingDto.getIntendUse());
       existingProjectBooking.setOccupantsNumber(projectBookingDto.getOccupantsNumber());
       existingProjectBooking.setTimeLine(projectBookingDto.getTimeLine());
       existingProjectBooking.setProjectPrice(projectBookingDto.getProjectPrice());




       // Lưu ProjectBooking đã cập nhật vào cơ sở dữ liệu
       return projectBookingRepository.save(existingProjectBooking);
   }


    @Override
    public ProjectBooking getProjectBookingById(Long projectBookingId) {

        Optional<ProjectBooking> bookingOptional = projectBookingRepository.findById(projectBookingId);
        return bookingOptional.orElse(null);
    }
    public List<ProjectBooking> getProjectBookingsByUserId(Long userId) {
        return projectBookingRepository.findByUserId(userId);
    }

}
