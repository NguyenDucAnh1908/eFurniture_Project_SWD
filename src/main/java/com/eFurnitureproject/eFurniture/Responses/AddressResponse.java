package com.eFurnitureproject.eFurniture.Responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String province;
    private String country;
    private String phoneNumber;
    private String postalCode;
    //private Long userAddressId;
}
