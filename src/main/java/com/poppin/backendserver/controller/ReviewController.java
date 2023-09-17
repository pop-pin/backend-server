package com.poppin.backendserver.controller;

import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.service.LocationService;
import com.poppin.backendserver.service.ReviewService;
import com.poppin.backendserver.dto.ReviewDTO;
import com.poppin.backendserver.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final LocationService locationService;


    /**
     * 특정 리뷰 가져오기
     */
    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("review_id") Long id){
        Optional<Review> review = reviewService.getReview(id);
        if (review.isPresent()) {
            ReviewDTO reviewDTO = new ReviewDTO(review.get());
            return ResponseEntity.ok(reviewDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 리뷰 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .rating(reviewDTO.getRating())
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .location(locationService.getLocation(reviewDTO.getLocationId()).get())
                .build();
        Long savedReviewId = reviewService.saveReview(review);
        return ResponseEntity.ok(savedReviewId);
    }

    /**
     * 특정 리뷰 업데이트
     */
    @PutMapping("/{review_id}")
    public ResponseEntity<Long> updateReview(@PathVariable("review_id") Long id, @RequestBody ReviewDTO reviewDTO) {
        Optional<Review> savedReview = reviewService.getReview(id);
        if (savedReview.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Review review = Review.builder()
                .rating(reviewDTO.getRating())
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .build();
        reviewService.updateReview(id, review);
        return ResponseEntity.ok(id);
    }

    /**
     * 특정 리뷰 삭제
     */
    @DeleteMapping("/{review_id}")
    public ResponseEntity<Long> deleteReview(@PathVariable("review_id") Long id) {
        Optional<Review> savedReview = reviewService.getReview(id);
        if (savedReview.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Long deletedReviewId = reviewService.deleteReview(id);
        return ResponseEntity.ok(deletedReviewId);
    }

}
