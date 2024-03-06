package com.eFurnitureproject.eFurniture.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalInfoDto {

    private String schedule;
    private String notes;
    private Long designerId;
}
