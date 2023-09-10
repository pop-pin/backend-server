package com.poppin.backendserver.dto;

import com.poppin.backendserver.entity.User;
import com.poppin.backendserver.enums.Gender;
import com.poppin.backendserver.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private Gender gender;
    private String phone;
    private int age;
    private String profileImageUrl;
    private String email;
    private UserRole userRole;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.age = user.getAge();
        this.profileImageUrl = user.getProfileImageUrl();
        this.email = user.getEmail();
        this.userRole = user.getUserRole();
    }

    @Builder
    public UserDTO(Long id, String name, Gender gender, String phone, int age, String profileImageUrl, String email, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.userRole = userRole;
    }
}
