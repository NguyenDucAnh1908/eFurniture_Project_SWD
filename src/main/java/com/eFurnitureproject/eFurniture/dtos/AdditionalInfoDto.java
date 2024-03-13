package com.eFurnitureproject.eFurniture.dtos;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalInfoDto {
    private Date schedule;
    private Long designerId;
}
