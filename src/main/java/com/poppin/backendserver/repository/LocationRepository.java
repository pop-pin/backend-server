package com.poppin.backendserver.repository;

import com.poppin.backendserver.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Location> findByName(String name);

}
