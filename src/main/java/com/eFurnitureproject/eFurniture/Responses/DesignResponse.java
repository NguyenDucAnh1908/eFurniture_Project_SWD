package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.ProjectBooking;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DesignResponse {
    private Long id;
    private String codeDesign;
    private String imageUrls;
    private String status;
    private String note;
    private String fileName;
    private String fileType;
    private ProjectBooking projectBooking;
    private String staffName;

}
