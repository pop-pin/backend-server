package com.poppin.backendserver.controller;

import com.poppin.backendserver.dto.ReviewDTO;
import com.poppin.backendserver.service.LocationService;
import com.poppin.backendserver.dto.LocationDTO;
import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    /**
     * 검색 및 페이징
     */
    @GetMapping("/search")
        public Page<LocationDTO> searchLocation(@RequestParam("keyword") String keyword, @PageableDefault(size = 6, sort = "id",direction = Sort.Direction.DESC)Pageable pageable) {
        Page<Location> page = locationService.searchLocation(keyword, pageable);
        return page.map(member -> new LocationDTO(member.getId(), member.getName(), member.getAddress(), member.getTelephone(), member.getRating(), member.getClosedDay(), member.getOpenTime(), member.getLatitude(), member.getLontitude()));
    }

    @GetMapping("/{location_id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable("location_id") Long id) {
        Optional<Location> optionalLocation = locationService.getLocation(id);
        if(optionalLocation.isPresent()) {
            LocationDTO locationDTO = convertToDTO(optionalLocation.get());
            return ResponseEntity.ok(locationDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{location_id}/reviews")
    public ResponseEntity<ReviewDTO> getReviews(@PathVariable("location_id") Long id) {
        return ResponseEntity.ok((ReviewDTO) reviewService.getReview(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createLocation(@RequestBody LocationDTO locationDTO) {
        Location location = convertToEntity(locationDTO);
        Long savedLocationId = locationService.saveLocation(location);
        return ResponseEntity.ok(savedLocationId);
    }

    @PutMapping("/{location_id}")
    public ResponseEntity<Long> updateLocation(@PathVariable("location_id") Long id, @RequestBody LocationDTO locationDTO) {
        Long updatedLocationId = locationService.updateLocation(id, convertToEntity(locationDTO));
        return ResponseEntity.ok(updatedLocationId);
    }

    @DeleteMapping("/{location_id}")
    public ResponseEntity<Long> deleteLocation(@PathVariable("location_id") Long id) {
        Long deletedLocationId = locationService.deleteLocation(id);
        return ResponseEntity.ok(deletedLocationId);
    }

    private LocationDTO convertToDTO(Location location) {
        LocationDTO locationDTO = LocationDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .address(location.getAddress())
                .telephone(location.getTelephone())
                .rating(location.getRating())
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
                .rating(locationDTO.getRating())
                .closedDay(locationDTO.getClosedDay())
                .openTime(locationDTO.getOpenTime())
                .latitude(locationDTO.getLatitude())
                .lontitude(locationDTO.getLontitude())
                .build();
        return location;
    }
}
