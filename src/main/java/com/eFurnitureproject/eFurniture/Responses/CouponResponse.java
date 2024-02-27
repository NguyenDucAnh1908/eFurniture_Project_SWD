package com.eFurnitureproject.eFurniture.Responses;

import com.eFurnitureproject.eFurniture.models.CouponCondition;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponResponse {
    private Long id;

    private String code;

    private boolean active;

    private List<CouponCondition> couponCondition;

}
