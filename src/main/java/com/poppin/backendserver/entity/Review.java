package com.poppin.backendserver.entity;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    private String title;
    private String text;
}
