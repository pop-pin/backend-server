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
     * 검색 및 페이징
     * 테스트완료
     */
    @GetMapping("/search")
        public Page<LocationDTO> searchLocation(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Location> pages = locationService.searchLocation(keyword, pageable);
        return pages.map(LocationDTO::new);
    }

    /**
     * 특정 가게 정보 가져오기
     * 테스트완료
     */
    @GetMapping("/{location_id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable("location_id") Long id) {
        Optional<Location> location = locationService.getLocation(id);
        if(location.isPresent()) {
            LocationDTO locationDTO = convertToDTO(location.get());
            return ResponseEntity.ok(locationDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 가게의 리뷰 가져오기
     * 테스트 완료
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
     * 테스트완료
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createLocation(@RequestBody LocationDTO locationDTO) {
        Location location = convertToEntity(locationDTO);
        Long savedLocationId = locationService.saveLocation(location);
        return ResponseEntity.ok(savedLocationId);
    }

    /**
     * 가게 정보 업데이트
     * 테스트완료
     */
    @PutMapping("/{location_id}")
    public ResponseEntity<Long> updateLocation(@PathVariable("location_id") Long id, @RequestBody LocationDTO locationDTO) {
        locationService.updateLocation(id, convertToEntity(locationDTO));
        return ResponseEntity.ok(id);
    }

    /**
     * 가게 삭제
     * 테스트완료
     */
    @DeleteMapping("/{location_id}")
    public ResponseEntity<Long> deleteLocation(@PathVariable("location_id") Long id) {
        Long deletedLocationId = locationService.deleteLocation(id);
        return ResponseEntity.ok(deletedLocationId);
    }

    /**
     * 테스트 위한 더미데이터
     */
    @PostConstruct
    public void init(){
        for (int i = 0; i < 100; i++) {
            locationService.saveLocation(Location.builder()
                    .name("apple"+i)
                    .address("korea"+i)
                    .telephone("0101234"+i)
                    .closedDay("monday")
                    .openTime("10~21")
                    .latitude((long) (10+i))
                    .lontitude((long) (30+i))
                    .build());
        }
    }

    private LocationDTO convertToDTO(Location location) {
        LocationDTO locationDTO = LocationDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .address(location.getAddress())
                .telephone(location.getTelephone())
                .ratingSum(location.getRatingSum())
                .ratingCount(location.getRatingCount())
                .openTime(location.getOpenTime())
                .closedDay(location.getClosedDay())
                .latitude(location.getLatitude())
                .lontitude(location.getLontitude())
                .build();
        return locationDTO;
    }

    private Location convertToEntity(LocationDTO locationDTO) {
        Location location = Location.builder()
                .id(locationDTO.getId())
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
        return location;
    }
}
