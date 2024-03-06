package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.dtos.BookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.services.impl.BookingService;
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

    @PostMapping("/register-booking")
    public ResponseEntity<?> registerBooking(@RequestBody BookingDto bookingDto) {
        try {
            BookingDto createdBooking = bookingService.registerBooking(bookingDto);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Data not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

}
