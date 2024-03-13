package com.eFurnitureproject.eFurniture.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto extends BaseDto{
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
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
    private Long designerId;
    private Date schedule;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}