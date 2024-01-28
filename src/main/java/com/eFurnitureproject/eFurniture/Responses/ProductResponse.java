package com.eFurnitureproject.eFurniture.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{
    private Long id;
    private String name;
    private String description;
    private String thumbnail;
    private double price;
    @JsonProperty("price_sale")
    private double priceSale;
    private int quantity;
    private String material;
    private String size;
    private int color;
    @JsonProperty("code_product")
    private String codeProduct;
    @JsonProperty("quantity_sold")
    private int quantitySold;
    private int status;
    private String discount;
    @JsonProperty("category_id")
    private Long categoryId;  // Field to represent Category ID
    @JsonProperty("brand_id")
    private Long brandId;     // Field to represent Brand ID
    @JsonProperty("tags_product_id")
    private Long tagsProductId; // Field to represent TagsProduct ID
}
