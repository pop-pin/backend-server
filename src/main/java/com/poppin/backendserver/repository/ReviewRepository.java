package com.poppin.backendserver.repository;

import com.poppin.backendserver.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByLocationId(Long location_id);

    List<Review> findByUserId(Long user_id);
}

