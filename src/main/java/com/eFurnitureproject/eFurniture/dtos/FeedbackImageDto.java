package com.eFurnitureproject.eFurniture.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackImageDto {

    private Long id;

    @NotBlank(message = "ImageUrl cannot be blank")
    private String imageUrl;
}
