package com.eFurnitureproject.eFurniture.Responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private String phoneNumber;
    private String email;
    private String addressType;
    private int defaultAddress;
    private Long userAddressId; // Assuming you want to use userAddressId instead of User object directly
}
