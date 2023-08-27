package com.poppin.backendserver.dto;

import com.poppin.backendserver.entity.Review;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private int rating;
    private String title;
    private String text;
    private Long locationId;
    private Long userId;


    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.text = review.getText();
        this.locationId = review.getLocation().getId();

    }
    @Builder
    public ReviewDTO(Long id, int rating, String title, String text, Long locationId, Long userId) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.text = text;
        this.locationId = locationId;
        this.userId = userId;
    }
}
