package com.poppin.backendserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String telephone;
    private Long ratingSum;
    private Long ratingCount;
    private String closedDay;
    @Column(nullable = false)
    private String openTime;
    private Long latitude;
    private Long lontitude;
    @JsonIgnore
    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<Category_item> category_items = new ArrayList<>();

    @Builder
    public Location(Long id, String name, String address, String telephone, Long ratingSum, Long ratingCount, String closedDay, String openTime, Long latitude, Long lontitude) {
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
