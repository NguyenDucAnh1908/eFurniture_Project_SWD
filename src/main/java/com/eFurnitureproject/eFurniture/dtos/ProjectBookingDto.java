package com.eFurnitureproject.eFurniture.dtos;

import lombok.*;

        @Data
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public class ProjectBookingDto extends BaseDto{

                private Long id;
                private String projectName;
                private String projectType;
                private String size;
                private String designStyle;
                private String colorSchemes;
                private String intendUse;
                private String occupantsNumber;
                private String timeLine;
                private String projectPrice;
                private String code;
                private Long userId; // Giả sử bạn chỉ muốn truyền userId thay vì toàn bộ đối tượng User
                private Long bookingId; // Tương tự như trên, chỉ truyền ID thay vì toàn bộ đối tượng Booking


        }
