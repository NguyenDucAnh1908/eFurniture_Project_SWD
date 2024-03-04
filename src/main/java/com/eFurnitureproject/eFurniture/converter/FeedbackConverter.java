package com.eFurnitureproject.eFurniture.converter;

import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.dtos.FeedbackImageDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Feedback;
import com.eFurnitureproject.eFurniture.models.FeedbackImages;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;  // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
                .reply(feedback.getReply())
                .updatedAt(feedback.getUpdatedAt())
                .createdAt(feedback.getCreatedAt())
                .userFullName(feedback.getUser().getFullName())
                .productId(feedback.getProduct() != null ? feedback.getProduct().getId() : null);


        // Add user mapping
        if (feedback.getUser() != null) {
            builder.userId(feedback.getUser().getId());
        }

        return builder.build();
    }

    public static Feedback toEntity(FeedbackDto feedbackDto) throws DataNotFoundException {
        return Feedback.builder()
                .id(feedbackDto.getId())
                .rating(feedbackDto.getRating())
                .comment(feedbackDto.getComment())
                .status(feedbackDto.getStatus())
                .reply(feedbackDto.getReply())
                .user(userRepository.findById(feedbackDto.getUserId())
                        .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + feedbackDto.getUserId())))
                .build();
    }

}
