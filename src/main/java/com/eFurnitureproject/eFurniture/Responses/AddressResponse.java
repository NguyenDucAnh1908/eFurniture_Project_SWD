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
    private String wardCode;
    private String districtCode;
    private String provinceCode;
    private String wardName;
    private String districtName;
    private String provinceName;
    private String phoneNumber;
    private String status;


    private UserResponse user;

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
