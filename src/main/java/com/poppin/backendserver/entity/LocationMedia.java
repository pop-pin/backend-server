package com.poppin.backendserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class LocationMedia {

    @Id @GeneratedValue
    private Long id;
    private String url;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
