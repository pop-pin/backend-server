package com.poppin.backendserver.controller;

import com.poppin.backendserver.dto.ReviewDTO;
import com.poppin.backendserver.entity.Review;
import com.poppin.backendserver.entity.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poppin.backendserver.service.LocationService;
import com.poppin.backendserver.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private LocationService locationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetReview() throws Exception {
        Location location = Location.builder()
                .name("appler")
                .address("korear")
                .telephone("0101234r")
                .closedDay("monday")
                .openTime("10~21")
                .latitude((long) (101))
                .lontitude((long) (303))
                .build();

        Review mockReview = Review.builder()
                .id(1L)
                .rating(5)
                .title("Test Title")
                .text("Test Text")
                .location(location)
                .build();

        when(reviewService.getReview(1L)).thenReturn(Optional.of(mockReview));

        mockMvc.perform(get("/v1/reviews/{review_id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.text").value("Test Text"));
    }

    @Test
    void testCreateReview() throws Exception {
        Location location = Location.builder()
                .id(1L)
                .name("appler")
                .address("korear")
                .telephone("0101234r")
                .closedDay("monday")
                .openTime("10~21")
                .latitude((long) (101))
                .lontitude((long) (303))
                .build();

        when(locationService.getLocation(1L)).thenReturn(Optional.of(location));

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .rating(5)
                .title("Test Title")
                .text("Test Text")
                .locationId(1L)
                .userId(1L)
                .build();

        mockMvc.perform(post("/v1/reviews/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reviewDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testUpdateReview() throws Exception {
        ReviewDTO updatedReviewDTO = ReviewDTO.builder()
                .id(1L)
                .rating(4)
                .title("Updated Title")
                .text("Updated Text")
                .locationId(1L)
                .userId(1L)
                .build();

        mockMvc.perform(put("/v1/reviews/{review_id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedReviewDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testDeleteReview() throws Exception {
        Long mockReviewId = 1L;

        when(reviewService.deleteReview(mockReviewId)).thenReturn(mockReviewId);

        mockMvc.perform(delete("/v1/reviews/{review_id}", mockReviewId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}