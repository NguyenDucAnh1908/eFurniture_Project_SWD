package com.eFurnitureproject.eFurniture.dtos;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto extends BaseDto{
    private String streetAddress;
    private String wardCode;
    private String districtCode;
    private String provinceCode;
    private String wardName;
    private String districtName;
    private String provinceName;
    private String status;
    private String note;
    private Long userId;
}