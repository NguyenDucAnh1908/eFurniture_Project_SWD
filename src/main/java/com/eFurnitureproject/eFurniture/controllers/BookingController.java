package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.dtos.AdditionalInfoDto;
import com.eFurnitureproject.eFurniture.dtos.BookingDto;
import com.eFurnitureproject.eFurniture.dtos.ProjectBookingDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Booking;
import com.eFurnitureproject.eFurniture.models.Design;
import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import com.eFurnitureproject.eFurniture.services.impl.BookingService;
import com.eFurnitureproject.eFurniture.services.impl.ProjectBookingService;
import com.eFurnitureproject.eFurniture.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/booking")
@RequiredArgsConstructor
@CrossOrigin
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectBookingService projectBookingService;
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
    @PostMapping("/register-project-booking")
    public ResponseEntity<?> registerProjectBooking(@RequestBody ProjectBookingDto projectBookingDto) {
        try {
            ProjectBookingDto createdProjectBooking = projectBookingService.createProjectBooking(projectBookingDto);
            return new ResponseEntity<>(createdProjectBooking, HttpStatus.CREATED);
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
    public ResponseEntity<Page<BookingDto>> getAllBookings(Pageable pageable) {
        Page<BookingDto> bookingDtos = bookingService.getAllBookingDtos(pageable);
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    @PostMapping("/design")
    public ResponseEntity<Design> createDesign(@RequestBody Design design) {
        Design createdDesign = bookingService.createDesign(design);
        return new ResponseEntity<>(createdDesign, HttpStatus.CREATED);
    }
    @DeleteMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        try {
            userService.cancelBooking(bookingId);
            return new ResponseEntity<>("Booking canceled successfully", HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Booking not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/receive-booking-request/{bookingId}")
    public ResponseEntity<?> receiveConsultation(@PathVariable Long bookingId, @RequestBody AdditionalInfoDto additionalInfoDto) {
        try {
            userService.receiveAndConfirmConsultation(bookingId, additionalInfoDto);
            return new ResponseEntity<>("Booking request received and confirmed successfully", HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Booking request not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @PutMapping("/updateProjectBooking/{id}")
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
    }*/
   @PutMapping("/updateProjectBooking/{id}")
   public ResponseEntity<?> updateProjectBooking(@PathVariable("id") Long id, @RequestBody ProjectBookingDto projectBookingDto) {
       try {
           ProjectBooking updatedProjectBooking = projectBookingService.updateProjectBooking(id, projectBookingDto);
           return ResponseEntity.ok(updatedProjectBooking);
       } catch (DataNotFoundException e) {
           return ResponseEntity.notFound().build();
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
       }
   }
//    @GetMapping("getProjectBookingbyId/{projectbookingId}")
//    public ResponseEntity<ProjectBooking> getProjectBookingById(@PathVariable Long projectbookingId) {
//        ProjectBooking projectBooking = projectBookingService.getProjectBookingById(projectbookingId);
//        if (projectBooking != null) {
//            return new ResponseEntity<>(projectBooking, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }x
@GetMapping("/getProjectBookingbyId/{projectBookingId}")
public ResponseEntity<ProjectBookingDto> getProjectBookingById(@PathVariable Long projectBookingId) {
    ProjectBookingDto projectBookingDto = projectBookingService.getProjectBookingById(projectBookingId);
    return ResponseEntity.ok(projectBookingDto);
}
    @GetMapping("/getProjectBookingbyUserid/{userId}")
    public ResponseEntity<?> getProjectBookingsByUserId(@PathVariable("userId") Long userId) {
        List<ProjectBooking> projectBookings = projectBookingService.getProjectBookingsByUserId(userId);
        return ResponseEntity.ok(projectBookings);
    }
    @GetMapping("/getProjectBookingByCode/{code}")
    public ResponseEntity<?> getProjectBookingByCode(@PathVariable String code) {
        try {
            ProjectBookingDto projectBooking = projectBookingService.getProjectBookingByCode(code);
            return new ResponseEntity<>(projectBooking, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("ProjectBooking not found with code: " + code, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllProjectBooking")
    public ResponseEntity<?> getAllProjectBookings() {
        try {
            List<ProjectBookingDto> projectBookingDtos = projectBookingService.getAllProjectBookings();
            return new ResponseEntity<>(projectBookingDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/by-booking-id/{bookingId}")
    public ResponseEntity<?> getProjectBookingByBookingId(@PathVariable Long bookingId) {
        try {
            ProjectBookingDto projectBookingDto = projectBookingService.getProjectBookingByBookingId(bookingId);
            return new ResponseEntity<>(projectBookingDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-by-user-id/{userId}/")
    public ResponseEntity<Page<BookingDto>> getAllBookingsByUserId(Pageable pageable,
     @PathVariable Long userId) {
        Page<BookingDto> bookingDtos = bookingService.getAllBookingDtosByUserId(pageable, userId);
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

}
