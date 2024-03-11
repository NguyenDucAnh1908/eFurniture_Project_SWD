package com.eFurnitureproject.eFurniture.models.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_VIEW("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    STAFF_VIEW("staff:read"),
    STAFF_UPDATE("staff:update"),
    STAFF_CREATE("staff:create"),
    STAFF_DELETE("staff:delete"),

    USER_VIEW("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),

    STAFF_DELIVERY_VIEW("staffDelivery:read"),
    STAFF_DELIVERY_UPDATE("staffDelivery:update"),
    STAFF_DELIVERY_CREATE("staffDelivery:create"),
    STAFF_DELIVERY_DELETE("staffDelivery:delete"),

    DESIGNER_VIEW("designer:read"),
    DESIGNER_UPDATE("designer:update"),
    DESIGNER_CREATE("designer:create"),
    DESIGNER_DELETE("designer:delete");



    @Getter
    private final String perminssion;
}
