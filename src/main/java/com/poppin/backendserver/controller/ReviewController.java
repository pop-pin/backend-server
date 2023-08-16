package com.poppin.backendserver.controller;

import com.poppin.backendserver.service.ReviewService;
import com.poppin.backendserver.dto.ReviewDTO;
import com.poppin.backendserver.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Long> createReview(@RequestBody ReviewDTO reviewDTO) {
        Long savedReviewId = reviewService.saveReview(convertToEntity(reviewDTO));
        return ResponseEntity.ok(savedReviewId);
    }

    @PutMapping("/{review_id}")
    public ResponseEntity<Long> updateReview(@PathVariable("review_id") Long id, @RequestBody ReviewDTO reviewDTO) {
        reviewService.updateReview(id, convertToEntity(reviewDTO));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{review_id}")
    public ResponseEntity<Long> deleteReview(@PathVariable("review_id") Long id) {
        Long deletedReviewId = reviewService.deleteReview(id);
        return ResponseEntity.ok(deletedReviewId);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .userId(review.getId())
                .rating(review.getRating())
                .title(review.getTitle())
                .locationId(review.getLocation().getId())
//                .userId(review.getUser().getId())
                .build();
        return reviewDTO;
    }
    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .id(reviewDTO.getId())
                .rating(reviewDTO.getRating())
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .build();
        return review;
    }
}
