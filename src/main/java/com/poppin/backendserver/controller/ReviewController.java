package com.poppin.backendserver.controller;

import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.service.LocationService;
import com.poppin.backendserver.service.ReviewService;
import com.poppin.backendserver.dto.ReviewDTO;
import com.poppin.backendserver.entity.Review;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 테스트완료
     */
    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("review_id") Long id){
        Optional<Review> review = reviewService.getReview(id);
        if (review.isPresent()) {
            ReviewDTO reviewDTO = convertToDTO(review.get());
            return ResponseEntity.ok(reviewDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 리뷰 생성
     * 테스트완료
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createReview(@RequestBody ReviewDTO reviewDTO) {
        Long savedReviewId = reviewService.saveReview(convertToEntity(reviewDTO));
        return ResponseEntity.ok(savedReviewId);
    }

    /**
     * 특정 리뷰 업데이트
     * 테스트완료
     */
    @PutMapping("/{review_id}")
    public ResponseEntity<Long> updateReview(@PathVariable("review_id") Long id, @RequestBody ReviewDTO reviewDTO) {
        reviewService.updateReview(id, convertToEntity(reviewDTO));
        return ResponseEntity.ok(id);
    }

    /**
     * 특정 리뷰 삭제
     * 테스트완료
     */
    @DeleteMapping("/{review_id}")
    public ResponseEntity<Long> deleteReview(@PathVariable("review_id") Long id) {
        Long deletedReviewId = reviewService.deleteReview(id);
        return ResponseEntity.ok(deletedReviewId);
    }

    /**
     * 테스트 위한 더미데이터
     */
    @PostConstruct
    public void init(){
        Location location = Location.builder()
                .name("appler")
                .address("korear")
                .telephone("0101234r")
                .closedDay("monday")
                .openTime("10~21")
                .latitude((long) (101))
                .lontitude((long) (303))
                .build();
        locationService.saveLocation(location);
        for (int i = 0; i < 100; i++) {
            reviewService.saveReview(Review.builder()
                    .rating(4)
                    .title("Nice food"+i)
                    .text("so delicius"+i)
                    .location(location)
                    .build());
        }
    }
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .title(review.getTitle())
                .text(review.getText())
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
                .location(locationService.getLocation(reviewDTO.getLocationId()).get())
                .build();
        return review;
    }
}
