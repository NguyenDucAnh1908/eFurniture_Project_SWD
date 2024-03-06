package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.dtos.chartDto.FeedbackRatingCountDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFeedbackService {
    FeedbackDto createFeedback(FeedbackDto feedbackDto);

    FeedbackDto getFeedbackById(Long id);

    void deleteFeedback(Long id);

    Page<FeedbackDto> getAllFeedbacksForProduct(Long productId, int page, int size, Integer rating, boolean hasImage, boolean hasComment);

    double getAverageRatingForProduct(Long productId);

    FeedbackDto replyToFeedback(Long feedbackId, String reply, Long replierId);

    FeedbackDto updateFeedback(Long id, FeedbackDto updatedFeedbackDto);
    List<FeedbackRatingCountDto> getFeedbackCountByRating();
}
