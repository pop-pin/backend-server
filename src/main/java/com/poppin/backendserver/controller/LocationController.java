package com.poppin.backendserver.controller;

import com.poppin.backendserver.dto.ReviewDTO;
import com.poppin.backendserver.repository.LocationRepository;
import com.poppin.backendserver.service.LocationService;
import com.poppin.backendserver.dto.LocationDTO;
import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.service.ReviewService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final ReviewService reviewService;

    private final LocationRepository locationRepository;

    /**
     * 검색 및 페이징 1
     */
    @GetMapping("/search")
    public Page<LocationDTO> searchLocation(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Location> pages = locationService.searchLocation(keyword, pageable);
        return pages.map(LocationDTO::new);
    }

    /**
     * 특정 가게 정보 가져오기
     */
    @GetMapping("/{location_id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable("location_id") Long id) {
        Optional<Location> location = locationService.getLocation(id);
        if (location.isPresent()) {
            LocationDTO locationDTO = new LocationDTO(location.get());
            return ResponseEntity.ok(locationDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 가게의 리뷰 가져오기
     */
    @GetMapping("/{location_id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable("location_id") Long id) {
        List<ReviewDTO> reviewDTO = reviewService.getLocationReview(id).stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTO);
    }

    /**
     * 가게 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createLocation(@RequestBody LocationDTO locationDTO) {
        Location location = Location.builder()
                .name(locationDTO.getName())
                .address(locationDTO.getAddress())
                .telephone(locationDTO.getTelephone())
                .ratingSum(locationDTO.getRatingSum())
                .ratingCount(locationDTO.getRatingCount())
                .closedDay(locationDTO.getClosedDay())
                .openTime(locationDTO.getOpenTime())
                .latitude(locationDTO.getLatitude())
                .lontitude(locationDTO.getLontitude())
                .build();
        Long savedLocationId = locationService.saveLocation(location);
        return ResponseEntity.ok(savedLocationId);
    }

    /**
     * 가게 정보 업데이트
     */
    @PutMapping("/{location_id}")
    public ResponseEntity<Long> updateLocation(@PathVariable("location_id") Long id, @RequestBody LocationDTO locationDTO) {
        Location location = Location.builder()
                .telephone(locationDTO.getTelephone())
                .closedDay(locationDTO.getClosedDay())
                .openTime(locationDTO.getOpenTime())
                .build();
        locationService.updateLocation(id, location);
        return ResponseEntity.ok(id);
    }

    /**
     * 가게 삭제
     */
    @DeleteMapping("/{location_id}")
    public ResponseEntity<Long> deleteLocation(@PathVariable("location_id") Long id) {
        Long deletedLocationId = locationService.deleteLocation(id);
        return ResponseEntity.ok(deletedLocationId);
    }
}
