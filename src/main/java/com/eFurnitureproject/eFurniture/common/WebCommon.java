package com.eFurnitureproject.eFurniture.common;

import org.springframework.stereotype.Component;

@Component

public class WebCommon {
    public String generateCodeFromName(String name) {
        // Thực hiện logic tạo mã của bạn ở đây
        // Đơn giản, bạn có thể sử dụng một logic cơ bản như loại bỏ khoảng trắng và chuyển đổi thành chữ in hoa
        return name.replaceAll("\\s", "-").toLowerCase();
    }
}
