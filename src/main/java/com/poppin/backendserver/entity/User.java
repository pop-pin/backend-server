package com.poppin.backendserver.entity;

import com.poppin.backendserver.enums.Gender;
import com.poppin.backendserver.enums.UserRole;
import jakarta.persistence.*;

@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String profileImageUrl;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
