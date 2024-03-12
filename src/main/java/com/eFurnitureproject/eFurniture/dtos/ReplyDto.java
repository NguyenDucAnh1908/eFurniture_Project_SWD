package com.eFurnitureproject.eFurniture.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDto extends BaseDto{
    private Long id;

    private String userFullName;

    private String comment;

    private int level;

    private Long parentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ReplyDto(Long id, String userFullName, String comment, int level ) {
        this.id = id;
        this.userFullName = userFullName;
        this.comment = comment;
        this.level = level;

    }
}
