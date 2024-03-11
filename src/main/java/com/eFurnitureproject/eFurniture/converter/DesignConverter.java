package com.eFurnitureproject.eFurniture.converter;


import com.eFurnitureproject.eFurniture.Responses.DesignResponse;
import com.eFurnitureproject.eFurniture.models.Design;
import org.springframework.stereotype.Component;

@Component
public class DesignConverter {
    public static DesignResponse toResponse(Design design) {
        DesignResponse designResponse = DesignResponse.builder()
                .id(design.getId())
                .codeDesign(design.getCodeDesign())
                .imageUrls(design.getImageUrls())
                .note(design.getNote())
                .status(design.getStatus())
                .projectBooking(design.getProjectBooking())
                .fileName(design.getFileName())
                .fileType(design.getFileType())
                .staffName(design.getStaffName())
                .build();
        return designResponse;
    }

}
