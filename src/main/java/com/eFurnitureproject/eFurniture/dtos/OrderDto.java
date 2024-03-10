package com.eFurnitureproject.eFurniture.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    //private LocalDateTime orderDate;
    @NotNull(message = "orderStatus not null")
    private Long orderStatus;
    @NotNull(message = "paymentStatus not null")
    private Long paymentStatus;
    @JsonProperty("total_amount")
    @Min(value = 1, message = "Total money must be >= 0")
    private double totalAmount;
    @JsonProperty("payment_method")
    private String paymentMethod;
//    @JsonProperty("shipping_address")
//    private String shippingAddress;
//    @JsonProperty("tracking_number")
//    private String trackingNumber;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    private String notes;
    private String discounts;
    @JsonProperty("fullName")
    private String fullName;
    private String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String address;
    private Boolean active;
    @JsonProperty("cart_items")
    private List<CartItemDto> cartItems;
    //private int orderDetails;
    @JsonProperty("user_id")
    @Min(value = 1, message = "User's ID must be > 0")
    private Long userId;
    @JsonProperty("coupon_id")
    private Long couponId;
}
