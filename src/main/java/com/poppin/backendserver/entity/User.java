package com.poppin.backendserver.entity;

import com.poppin.backendserver.enums.Gender;
import com.poppin.backendserver.enums.UserRole;
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
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private int age;
    private String profileImageUrl;
    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;
//    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
//    private List<Review> reviews = new ArrayList<>();

    @Builder
    public User(String name, Gender gender, String phone, Integer age, String profileImageUrl, String email, UserRole userRole) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.userRole = userRole;
    }
}
