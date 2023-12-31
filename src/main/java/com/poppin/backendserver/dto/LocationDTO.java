package com.poppin.backendserver.dto;

import com.poppin.backendserver.entity.Location;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
public class LocationDTO {
    private Long id;
    private String name;
    private String address;
    private String telephone;
    private Long ratingSum;
    private Long ratingCount;
    private String closedDay;
    private String openTime;
    private Long latitude;
    private Long lontitude;

    public LocationDTO(Location location){
        this.id = location.getId();
        this.name = location.getName();
        this.address = location.getAddress();
        this.telephone = location.getTelephone();
        this.ratingSum = location.getRatingSum();
        this.ratingCount = location.getRatingCount();
        this.closedDay = location.getClosedDay();
        this.openTime = location.getOpenTime();
        this.latitude = location.getLatitude();
        this.lontitude = location.getLontitude();
    }
    @Builder
    public LocationDTO(Long id, String name, String address, String telephone, Long ratingSum, Long ratingCount, String closedDay, String openTime, Long latitude, Long lontitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.ratingSum = ratingSum;
        this.ratingCount = ratingCount;
        this.closedDay = closedDay;
        this.openTime = openTime;
        this.latitude = latitude;
        this.lontitude = lontitude;
    }
}
