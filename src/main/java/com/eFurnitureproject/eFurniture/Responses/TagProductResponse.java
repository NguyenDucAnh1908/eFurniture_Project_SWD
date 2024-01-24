package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.TagsProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagProductResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    private TagsProduct tagsProduct;
}
