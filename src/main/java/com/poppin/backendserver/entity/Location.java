package com.poppin.backendserver.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Location extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String number;
    private Double rating;
    private String closedDay;
    @Column(nullable = false)
    private String openTime;
    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Location(String name, String address, String number, Double rating, String closedDay, String openTime) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.rating = rating;
        this.closedDay = closedDay;
        this.openTime = openTime;
    }
}
