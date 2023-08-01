package com.poppin.backendserver.service;

import com.poppin.backendserver.entity.Review;
import com.poppin.backendserver.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(Review review) {
        reviewRepository.save(review);
        return review.getId();
    }

    public List<Review> getReview(Long location_id) {
        return reviewRepository.findByLocationId(location_id);
    }

    @Transactional
    public Long updateReview(Long id, Review updateReview) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setRating(updateReview.getRating());
            review.setTitle(updateReview.getTitle());
            review.setText(updateReview.getText());
            return updateReview.getId();
        } else {
            throw new IllegalStateException("리뷰 업데이트에 실패하였습니다.");
        }
    }

    public Long deleteReview(Long id) {
        reviewRepository.deleteById(id);
        return id;
    }

}
