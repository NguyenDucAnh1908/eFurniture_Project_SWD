package com.eFurnitureproject.eFurniture.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eFurnitureproject.eFurniture.converter.FeedbackConverter;
import com.eFurnitureproject.eFurniture.converter.ReplyConverter;
import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.dtos.ReplyDto;
import com.eFurnitureproject.eFurniture.dtos.chartDto.FeedbackRatingCountDto;
import com.eFurnitureproject.eFurniture.exceptions.DataNotFoundException;
import com.eFurnitureproject.eFurniture.models.Feedback;
import com.eFurnitureproject.eFurniture.models.Product;
import com.eFurnitureproject.eFurniture.models.Reply;
import com.eFurnitureproject.eFurniture.models.User;
import com.eFurnitureproject.eFurniture.repositories.FeedbackRepository;
import com.eFurnitureproject.eFurniture.repositories.ProductRepository;
import com.eFurnitureproject.eFurniture.repositories.ReplyRepository;
import com.eFurnitureproject.eFurniture.repositories.UserRepository;
import com.eFurnitureproject.eFurniture.services.IFeedbackService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReplyRepository replyRepository;
    private final Cloudinary cloudinary;


    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           ReplyRepository replyRepository, Cloudinary cloudinary) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.replyRepository = replyRepository;
        this.cloudinary = cloudinary;
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
            Feedback feedback = Feedback.builder()
                    .rating(feedbackDto.getRating())
                    .status(feedbackDto.getStatus())
                    .parentId(feedbackDto.getParentId())
                    .build();
            feedback.setProduct(existingProduct);
            feedback.setUser(user);

            // Analyze HTML content to extract image URLs
            Document doc = Jsoup.parse(feedbackDto.getComment());
            Elements imgTags = doc.select("img");
            List<String> imageURLs = new ArrayList<>();
            for (Element imgTag : imgTags) {
                String url = imgTag.attr("src");
                // Upload the image to Cloudinary and get the new URL
                String newURL = uploadToCloudinaryAndReturnURL(url);
                // Replace the old URL in the content with the new URL
                imgTag.attr("src", newURL);
                // Save the new URL to the list
                imageURLs.add(newURL);
            }

            // Save the list of new URLs to the imageUrls field
            feedback.setImageUrls(String.join(",", imageURLs));

            // Save the updated content to the Blog object
            String updatedComment = doc.html();
            feedback.setComment(updatedComment);

            // Save the feedback to the database
            feedback = feedbackRepository.save(feedback);

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
                existingFeedback.setStatus(updatedFeedbackDto.getStatus());

                // Save the updated feedback to the database
                existingFeedback = feedbackRepository.save(existingFeedback);

                return FeedbackConverter.toDto(existingFeedback);
            } else {
                throw new DataNotFoundException("Feedback not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating feedback: " + e.getMessage());
        }
    }

    @Override
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

    @Override
    public ReplyDto addReplyToFeedback(Long feedbackId, ReplyDto replyDto) {
        try {
            Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackId);

            if (optionalFeedback.isPresent()) {
                Feedback feedback = optionalFeedback.get();

                // Tạo một đối tượng Reply
                Reply replyEntity = ReplyConverter.toEntity(replyDto);
                replyEntity.setFeedback(feedback);
                replyEntity.setUserFullName(feedback.getUser().getFullName());
                replyEntity.setParentId(feedback.getId());
                feedback.getReplies().add(replyEntity);
                feedback = feedbackRepository.save(feedback);
                replyRepository.save(replyEntity);
                FeedbackConverter.toDto(feedback);
                return ReplyConverter.toDto(replyEntity);
            } else {
                throw new DataNotFoundException("Feedback not found with ID: " + feedbackId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error replying to feedback: " + e.getMessage());
        }
    }

    public Page<FeedbackDto> getAllFeedback(Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findAll(pageable);
        return feedbackPage.map(FeedbackConverter::toDto);
    }

    @Override
    public List<FeedbackDto> getAllFeedback() {
        List<Feedback> feedback = feedbackRepository.findAll();
        return FeedbackConverter.toDtoList(feedback);
    }

    @Override
    public List<FeedbackDto> getByParentId(Long parentId) {
        return feedbackRepository.findByParent(parentId);
    }

    @Override
    public Page<FeedbackDto> getAllFeedbackByUserId(Pageable pageable, Long userId) {
            Page<Feedback> feedbackPage = feedbackRepository.findAllByUserId(pageable,userId);
            return feedbackPage.map(FeedbackConverter::toDto);
        }


    @Transactional(readOnly = true)
    public Page<FeedbackDto> getAllFeedbacksForProduct(Long productId, int page, int size, Integer rating, boolean hasImage, boolean hasComment) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

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

        List<FeedbackDto> feedbackDtos = feedbacksPage.getContent().stream()
                .map(FeedbackConverter::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(feedbackDtos, pageable, feedbacksPage.getTotalElements());
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


    private String uploadToCloudinaryAndReturnURL(String imageURL) {
        try {
            // Upload img into Cloudinary
            Map uploadResult = cloudinary.uploader().upload(imageURL, ObjectUtils.emptyMap());

            // Return URL of img in Cloudinary
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            // Exception Cloudinary
            e.printStackTrace();
            return null;
        }
    }
}
