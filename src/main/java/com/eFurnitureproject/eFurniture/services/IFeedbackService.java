package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.dtos.ReplyDto;
import com.eFurnitureproject.eFurniture.dtos.chartDto.FeedbackRatingCountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFeedbackService {
    FeedbackDto createFeedback(FeedbackDto feedbackDto);

    FeedbackDto getFeedbackById(Long id);

    void deleteFeedback(Long id);

    Page<FeedbackDto> getAllFeedbacksForProduct(Long productId, int page, int size, Integer rating, boolean hasImage, boolean hasComment);

    double getAverageRatingForProduct(Long productId);

    FeedbackDto updateFeedback(Long id, FeedbackDto updatedFeedbackDto);
    List<FeedbackRatingCountDto> getFeedbackCountByRating();

    ReplyDto addReplyToFeedback(Long feedbackId, ReplyDto replyDto);

    Page<FeedbackDto> getAllFeedback(Pageable pageable);

    List<FeedbackDto> getAllFeedback();

    List<FeedbackDto> getByParentId(Long parentId);

    Page<FeedbackDto> getAllFeedbackByUserId(Pageable pageable, Long userId);
}