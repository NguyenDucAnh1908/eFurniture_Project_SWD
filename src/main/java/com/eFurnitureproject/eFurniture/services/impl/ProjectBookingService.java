package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.repositories.ProjectBookingRepository;
import com.eFurnitureproject.eFurniture.services.IProjectBookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProjectBookingService implements IProjectBookingService {
    private final ProjectBookingRepository projectBookingRepository;
    @Override
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
    }

    @Override
    public ProjectBooking getProjectBookingById(Long projectBookingId) {

        Optional<ProjectBooking> bookingOptional = projectBookingRepository.findById(projectBookingId);
        return bookingOptional.orElse(null);
    }
}
