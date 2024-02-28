package com.eFurnitureproject.eFurniture.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto{

    private Long id;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Street address is required")
    private String streetAddress;

    @NotBlank(message = "Province is required")
    private String province;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String postalCode;

    private String userAddressId; // Assuming you want to use userAddressId instead of User object directly
}
