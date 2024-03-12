package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.ReplyDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Reply;

import java.util.List;
import java.util.stream.Collectors;

public class ReplyConverter {


    public static ReplyDto toDto(Reply reply) {
        return ReplyDto.builder()
                .id(reply.getId())
                .comment(reply.getComment())
                .userFullName(reply.getFeedback().getUser().getFullName())
                .level(reply.getLevel())
                .parentId(reply.getFeedback().getParentId())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }

    public static Reply toEntity(ReplyDto replyDto) throws DataNotFoundException {
        return Reply.builder()
                .id(replyDto.getId())
                .level(replyDto.getLevel())
                .userFullName(replyDto.getUserFullName())
                .comment(replyDto.getComment())
                .build();
    }

    public static List<ReplyDto> toDtoList(List<Reply> replies) {
        return replies.stream()
                .map(ReplyConverter::toDto)
                .collect(Collectors.toList());
    }

}

