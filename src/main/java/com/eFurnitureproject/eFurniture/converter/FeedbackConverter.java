package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.dtos.ReplyDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Feedback;
import com.eFurnitureproject.eFurniture.models.Reply;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FeedbackConverter {

    private static UserRepository userRepository;  // Add UserRepository

    @Autowired
    public FeedbackConverter(UserRepository userRepository) {
        FeedbackConverter.userRepository = userRepository;
    }

    public static FeedbackDto toDto(Feedback feedback) {
        FeedbackDto.FeedbackDtoBuilder builder = FeedbackDto.builder()
                .id(feedback.getId())
                .rating(feedback.getRating())
                .comment(feedback.getComment())
                .status(feedback.getStatus())
                .updatedAt(feedback.getUpdatedAt())
                .createdAt(feedback.getCreatedAt())
                .parentId(feedback.getParentId())
                .userFullName(feedback.getUser().getFullName())
//                .replierName(feedback.getReplier() != null ? feedback.getReplier().getFullName() : null)
                .productId(feedback.getProduct() != null ? feedback.getProduct().getId() : null);

        // Add user mapping
        if (feedback.getUser() != null) {
            builder.userId(feedback.getUser().getId());
        }


        // Add mapping for replies
        if(feedback.getReplies() != null) {
            builder.replies(feedback.getReplies().stream()
                    .map(ReplyConverter::toDto)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }

    public static Feedback toEntity(FeedbackDto feedbackDto) {
        try {
            List<ReplyDto> replyDtos = feedbackDto.getReplies();
            List<Reply> replies = replyDtos.stream()
                    .map(replyDto -> {
                        try {
                            Reply reply = ReplyConverter.toEntity(replyDto);
                            reply.setParentId(feedbackDto.getParentId()); // Set parentId for replies
                            return reply;
                        } catch (DataNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return Feedback.builder()
                    .id(feedbackDto.getId())
                    .rating(feedbackDto.getRating())
                    .comment(feedbackDto.getComment())
                    .status(feedbackDto.getStatus())
                    .replies(replies)
                    .user(userRepository.findById(feedbackDto.getUserId())
                            .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + feedbackDto.getUserId())))
                    .build();
        } catch (DataNotFoundException e) {
            throw new RuntimeException("Error converting FeedbackDto to Feedback", e);
        }
    }


    public static List<FeedbackDto> toDtoList(List<Feedback> feedbackList) {
        return feedbackList.stream()
                .map(FeedbackConverter::toDto)
                .collect(Collectors.toList());
    }
}
