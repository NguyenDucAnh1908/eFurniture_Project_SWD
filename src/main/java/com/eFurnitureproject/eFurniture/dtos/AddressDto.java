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

    private String wardCode;
    private String districtCode;
    private String provinceCode;
    private String wardName;
    private String districtName;
    private String provinceName;
    private String status;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;


    private String userAddressId; // Assuming you want to use userAddressId instead of User object directly
}
