package com.eFurnitureproject.eFurniture.controllers;

import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.dtos.chartDto.FeedbackRatingCountDto;
import com.eFurnitureproject.eFurniture.services.IFeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/feedbacks")
@RequiredArgsConstructor
@CrossOrigin
public class FeedbackController {

    private final IFeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(@Valid @RequestBody FeedbackDto feedbackDto, BindingResult bindingResult) {
        ResponseEntity<?> validationErrors = getResponseEntity(bindingResult);
        if (validationErrors != null) return validationErrors;
        try {
            FeedbackDto createdFeedback = feedbackService.createFeedback(feedbackDto);
            return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackDto updatedFeedbackDto,
            BindingResult bindingResult) {

        ResponseEntity<?> validationErrors = getResponseEntity(bindingResult);
        if (validationErrors != null) return validationErrors;

        try {
            // Call the service method to update feedback
            FeedbackDto updatedFeedback = feedbackService.updateFeedback(id, updatedFeedbackDto);
            return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return 400 Bad Request with error details
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        return null;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<FeedbackDto>> getAllFeedbacksForProduct(
            @PathVariable Long productId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false, defaultValue = "false") boolean hasImage,
            @RequestParam(required = false, defaultValue = "false") boolean hasComment
    ) {

        try {
            Page<FeedbackDto> feedbacks = feedbackService.getAllFeedbacksForProduct(productId, page, size, rating, hasImage, hasComment);
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable Long id) {
        FeedbackDto feedback = feedbackService.getFeedbackById(id);
        return feedback != null ?
                new ResponseEntity<>(feedback, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/average-rating/{productId}")
    public ResponseEntity<Double> getAverageRatingForProduct(@PathVariable Long productId) {
        try {
            double averageRating = feedbackService.getAverageRatingForProduct(productId);
            return new ResponseEntity<>(averageRating, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reply/{feedbackId}")
    public ResponseEntity<FeedbackDto> replyToFeedback(@PathVariable Long feedbackId, @RequestParam String reply) {
        try {
            FeedbackDto repliedFeedback = feedbackService.replyToFeedback(feedbackId, reply);
            return new ResponseEntity<>(repliedFeedback, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/feedback/count-by-rating")
    public List<FeedbackRatingCountDto> getFeedbackCountByRating() {
        return feedbackService.getFeedbackCountByRating();
    }
}
