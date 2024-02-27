package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.AddressListResponse;
import com.eFurnitureproject.eFurniture.Responses.AddressResponse;
import com.eFurnitureproject.eFurniture.dtos.AddressDto;
import com.eFurnitureproject.eFurniture.models.Address;
import com.eFurnitureproject.eFurniture.services.IAddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/address")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

    @CrossOrigin
    @GetMapping("/get_all_address_by_id/{userId}")
    public ResponseEntity<AddressListResponse> getAllAddressesByUserId(
            @PathVariable Long userId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AddressResponse> addressesPage = addressService.getAllAddressesByUserId(userId, pageRequest, keyword);
            int totalPages = addressesPage.getTotalPages();
            List<AddressResponse> addressList = addressesPage.getContent();
            return ResponseEntity.ok(AddressListResponse.builder()
                    .address(addressList)
                    .totalPages(totalPages)
                    .build());
        }catch (IllegalArgumentException ex){
            AddressListResponse response = new AddressListResponse();
            response.setError("Invalid user ID: " + userId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @CrossOrigin
    @PostMapping("/create_address/{userId}")
    public ResponseEntity<?> createAddress(@RequestBody AddressDto addressDto, @PathVariable Long userId) {
        try {
            Address createdAddress = addressService.createAddress(addressDto, userId);
            return ResponseEntity.ok(createdAddress);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the address.");
        }
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody AddressDto addressDto) {
        try {
            Address updatedAddress = addressService.updateAddress(addressId, addressDto);
            return ResponseEntity.ok(updatedAddress);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the address.");
        }
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok().body("Address with ID " + addressId + " has been successfully deleted.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the address.");
        }
    }

}

