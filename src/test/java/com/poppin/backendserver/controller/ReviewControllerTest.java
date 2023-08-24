package com.poppin.backendserver.controller;

import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.entity.Review;
import com.poppin.backendserver.service.LocationService;
import com.poppin.backendserver.service.ReviewService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ReviewControllerTest {


    @Autowired
    LocationService locationService;
    @Autowired
    ReviewService reviewService;


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

    @Test
    public void ReviewControllerTest() throws Exception {
        // given
        init();
        // when

        // then
    }
}