package com.poppin.backendserver.service;

import com.poppin.backendserver.entity.Location;
import com.poppin.backendserver.entity.Review;
import com.poppin.backendserver.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Optional<Location> getLocation(Long id) {
        return locationRepository.findById(id);
    }

    public Page<Location> searchLocation(String keyword, Pageable pageable) {
        return locationRepository.findByKeyword(keyword, pageable);
    }
    @Transactional
    public Long saveLocation(Location location) {
        validateDuplicateLocation(location);
        locationRepository.save(location);
        return location.getId();
    }

    private void validateDuplicateLocation(Location location) {
        List<Location> findLocations = locationRepository.findByName(location.getName());
        if (!findLocations.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 가게입니다.");
        }
    }


    @Transactional
    public Long updateLocation(Long id, Location updatedLocation) {
        Optional<Location> optionalLocation = locationRepository.findById(id);

        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            location.setName(updatedLocation.getName());
            location.setTelephone(updatedLocation.getTelephone());
            location.setOpenTime(updatedLocation.getOpenTime());
            location.setClosedDay(updatedLocation.getClosedDay());
            return updatedLocation.getId();
        } else {
            throw new IllegalStateException("가게 정보 업데이트에 실패하였습니다.");
        }
    }


    public List<Review> getReviews(Long id) {
        return locationRepository.findById(id)
                .map(Location::getReviews)
                .orElseThrow(() -> new IllegalStateException("리뷰 불러오기에 실패하였습니다."));
    }
    @Transactional
    public Long deleteLocation(Long id) {
        locationRepository.deleteById(id);
        return id;
    }
}