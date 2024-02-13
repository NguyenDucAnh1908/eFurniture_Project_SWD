package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.Responses.AddressListResponse;
import com.eFurnitureproject.eFurniture.Responses.AddressResponse;
import com.eFurnitureproject.eFurniture.services.IAddressService;
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
@RequestMapping("api/address")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

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
}

