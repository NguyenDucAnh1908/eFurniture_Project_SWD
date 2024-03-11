package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.ReplyDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Reply;

public class ReplyConverter {


    public static ReplyDto toDto(Reply reply) {
        return ReplyDto.builder()
                .id(reply.getId())
                .reply(reply.getReplyText())
                .replierId(reply.getReplier() != null ? reply.getReplier().getId() : null)
                .replierName(reply.getReplier() != null ? reply.getReplier().getFullName() : null)
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }

    public static Reply toEntity(ReplyDto replyDto) throws DataNotFoundException {
        return Reply.builder()
                .id(replyDto.getId())
                .replyText(replyDto.getReply())
                .build();
    }
}

