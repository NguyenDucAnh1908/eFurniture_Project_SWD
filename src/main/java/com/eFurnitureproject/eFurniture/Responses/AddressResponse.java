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
    private String ward;
    private String district;
    private String province;
    private String phoneNumber;


    private UserResponse user;

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
