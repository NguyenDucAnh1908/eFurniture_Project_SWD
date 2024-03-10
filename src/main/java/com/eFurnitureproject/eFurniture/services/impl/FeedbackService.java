package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.converter.FeedbackConverter;
import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.dtos.chartDto.FeedbackRatingCountDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Feedback;
import com.eFurnitureproject.eFurniture.models.FeedbackImages;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.FeedbackImagesRepository;
import com.eFurnitureproject.eFurniture.repositories.FeedbackRepository;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackImagesRepository feedbackImagesRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository,
                           FeedbackImagesRepository feedbackImagesRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackImagesRepository = feedbackImagesRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        try {
            Product existingProduct = productRepository
                    .findById(feedbackDto.getProductId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find product with id: " + feedbackDto.getProductId()));

            User user = userRepository.findById(feedbackDto.getUserId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find user with id: " + feedbackDto.getUserId()));

            // Convert DTO to entity
            Feedback feedback = FeedbackConverter.toEntity(feedbackDto);
            feedback.setProduct(existingProduct);
            feedback.setUser(user);

            // Save the feedback to the database
            feedback = feedbackRepository.save(feedback);

            // Save images associated with the feedback
            if (feedbackDto.getImages() != null && !feedbackDto.getImages().isEmpty()) {
                final Feedback finalFeedback = feedback;
                List<FeedbackImages> feedbackImages = FeedbackConverter.toImageEntityList(feedbackDto.getImages());
                feedbackImages.forEach(image -> image.setFeedback(finalFeedback));
                feedbackImagesRepository.saveAll(feedbackImages);
            }

            return FeedbackConverter.toDto(feedback);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating feedback: " + e.getMessage());
        }
    }

    @Transactional
    public FeedbackDto updateFeedback(Long id, FeedbackDto updatedFeedbackDto) {
        try {
            // Find the existing feedback by ID
            Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);

            if (optionalFeedback.isPresent()) {
                // Update the existing feedback
                Feedback existingFeedback = optionalFeedback.get();

                existingFeedback.setRating(updatedFeedbackDto.getRating());
                existingFeedback.setComment(updatedFeedbackDto.getComment());
                existingFeedback.setDateFeedback(updatedFeedbackDto.getDateFeedback());
                existingFeedback.setStatus(updatedFeedbackDto.getStatus());
                existingFeedback.setReply(updatedFeedbackDto.getReply());

                // Save the updated feedback to the database
                existingFeedback = feedbackRepository.save(existingFeedback);

                // Update associated images (if any)
                if (updatedFeedbackDto.getImages() != null && !updatedFeedbackDto.getImages().isEmpty()) {
                    List<FeedbackImages> updatedImages = FeedbackConverter.toImageEntityList(updatedFeedbackDto.getImages());
                    Feedback finalExistingFeedback = existingFeedback;
                    updatedImages.forEach(image -> image.setFeedback(finalExistingFeedback));
                    feedbackImagesRepository.saveAll(updatedImages);
                }

                return FeedbackConverter.toDto(existingFeedback);
            } else {
                throw new DataNotFoundException("Feedback not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating feedback: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<FeedbackDto> getAllFeedbacksForProduct(Long productId, int page, int size, Integer rating, boolean hasImage, boolean hasComment) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateFeedback").descending());

        Specification<Feedback> spec = Specification.where((root, query, cb) -> cb.equal(root.get("product").get("id"), productId));

        if (rating != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("rating"), rating));
        }

        if (hasImage) {
            spec = spec.and((root, query, cb) -> cb.isNotEmpty(root.get("images")));
        }

        if (hasComment) {
            spec = spec.and((root, query, cb) -> cb.isNotEmpty(root.get("comment")));
        }

        Page<Feedback> feedbacksPage = feedbackRepository.findAll(spec, pageable);

        return feedbacksPage.map(FeedbackConverter::toDto);
    }

    @Transactional(readOnly = true)
    public FeedbackDto getFeedbackById(Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.map(FeedbackConverter::toDto).orElse(null);
    }

    @Transactional
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public double getAverageRatingForProduct(Long productId) {
        List<Feedback> feedbacks = feedbackRepository.findByProductId(productId);

        if (feedbacks.isEmpty()) {
            return 0;
        }

        double averageRating = feedbacks.stream()
                .mapToDouble(Feedback::getRating)
                .average()
                .orElse(0.0);

        // Round to the nearest integer
        return Math.round(averageRating * 10.0) / 10.0;
    }

    @Transactional
    public FeedbackDto replyToFeedback(Long feedbackId, String reply) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackId);

        if (optionalFeedback.isPresent()) {
            Feedback feedback = optionalFeedback.get();
            feedback.setReply(reply);

            feedback = feedbackRepository.save(feedback);

            return FeedbackConverter.toDto(feedback);
        } else {
            throw new RuntimeException("Feedback not found with ID: " + feedbackId);
        }
    }

    public List<FeedbackRatingCountDto> getFeedbackCountByRating() {
        List<Object[]> result = feedbackRepository.findFeedbackCountByRating();
        return result.stream()
                .map(row -> {
                    int rating = (int) row[0];
                    long count = (long) row[1];
                    return new FeedbackRatingCountDto(rating, count);
                })
                .collect(Collectors.toList());
    }
}
