package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Feedback;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                .userFullName(feedback.getUser().getFullName())
                .replierName(feedback.getReplier() != null ? feedback.getReplier().getFullName() : null)
                .productId(feedback.getProduct() != null ? feedback.getProduct().getId() : null);

        // Add user mapping
        if (feedback.getUser() != null) {
            builder.userId(feedback.getUser().getId());
        }

        if (feedback.getReplier() != null) {
            builder.replierId(feedback.getReplier().getId());
        }
        // Add mapping for replies
        builder.replies(feedback.getReplies().stream()
                .map(ReplyConverter::toDto)
                .collect(Collectors.toList()));

        return builder.build();
    }

    public static Feedback toEntity(FeedbackDto feedbackDto) throws DataNotFoundException {
        return Feedback.builder()
                .id(feedbackDto.getId())
                .rating(feedbackDto.getRating())
                .comment(feedbackDto.getComment())
                .status(feedbackDto.getStatus())
                .user(userRepository.findById(feedbackDto.getUserId())
                        .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + feedbackDto.getUserId())))
                .build();
    }

}
