package com.poppin.backendserver.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class LocationDTO {
    private Long id;
    private String name;
    private String address;
    private String telephone;
    private Double rating;
    private String closedDay;
    private String openTime;
    private Long latitude;
    private Long lontitude;
    private List<ReviewDTO> reviews;

    @Builder
    public LocationDTO(Long id, String name, String address, String telephone, Double rating, String closedDay, String openTime, Long latitude, Long lontitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.rating = rating;
        this.closedDay = closedDay;
        this.openTime = openTime;
        this.latitude = latitude;
        this.lontitude = lontitude;
    }
}
