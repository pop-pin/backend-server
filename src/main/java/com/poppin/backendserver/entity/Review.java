package com.poppin.backendserver.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    @Column(nullable = false)
    private int overallRating;
    @Column(nullable = false)
    private int foodRating;
    @Column(nullable = false)
    private int serviceRating;
    @Column(nullable = false)
    private int moodRating;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Review(int overallRating, int foodRating, int serviceRating, int moodRating, String title, String text) {
        this.overallRating = overallRating;
        this.foodRating = foodRating;
        this.serviceRating = serviceRating;
        this.moodRating = moodRating;
        this.title = title;
        this.text = text;
    }
}
