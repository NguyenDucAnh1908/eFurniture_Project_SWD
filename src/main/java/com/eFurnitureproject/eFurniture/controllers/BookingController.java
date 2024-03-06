package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.services.impl.BookingService;
import com.eFurnitureproject.eFurniture.services.impl.ProjectBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    @Autowired
    private BookingService bookingService;
    private ProjectBookingService projectBookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PostMapping("/design")
    public ResponseEntity<Design> createDesign(@RequestBody Design design) {
        Design createdDesign = bookingService.createDesign(design);
        return new ResponseEntity<>(createdDesign, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProjectBooking> updateProjectBooking(@PathVariable Long id, @RequestBody ProjectBooking projectBooking) {
        // Kiểm tra xem ProjectBooking có tồn tại không trước khi cập nhật
        ProjectBooking existingProjectBooking = projectBookingService.getProjectBookingById(id);
        if (existingProjectBooking == null) {
            return ResponseEntity.notFound().build();
        }

        // Đảm bảo ID từ URL được đặt cho projectBooking để tránh các vấn đề không đồng nhất
        projectBooking.setId(id);
        ProjectBooking updatedProjectBooking = projectBookingService.updateProjectBooking(projectBooking);

        if(updatedProjectBooking != null) {
            return ResponseEntity.ok(updatedProjectBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{projectBookingId}")
    public ResponseEntity<ProjectBooking> getProjectBookingById(@PathVariable Long projectBookingId) {
        ProjectBooking projectBooking = projectBookingService.getProjectBookingById(projectBookingId);
        if (projectBooking != null) {
            return new ResponseEntity<>(projectBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
