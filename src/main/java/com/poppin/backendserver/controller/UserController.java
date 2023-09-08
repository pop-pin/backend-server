package com.poppin.backendserver.controller;

import com.poppin.backendserver.dto.UserDTO;
import com.poppin.backendserver.entity.User;
import com.poppin.backendserver.repository.UserRepository;
import com.poppin.backendserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * 특정 유저 정보 가져오기
     */
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("user_id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            UserDTO userDto = new UserDTO(user.get());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 유저 정보 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .gender((userDTO.getGender()))
                .phone(userDTO.getPhone())
                .age(userDTO.getAge())
                .profileImageUrl(userDTO.getProfileImageUrl())
                .email(userDTO.getEmail())
                .userRole(userDTO.getUserRole())
                .build();

        Long savedUserId = userService.saveUser(user);
        return ResponseEntity.ok(savedUserId);
    }

    /**
     * 유저 정보 업데이트
     */
    @PutMapping("/{user_id}")
    public ResponseEntity<Long> updateUser(@PathVariable("user_id") Long id, @RequestBody UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .profileImageUrl(userDTO.getProfileImageUrl())
                .email(userDTO.getEmail())
                .build();
        userService.updateUser(id, user);
        return ResponseEntity.ok(id);
    }

    /**
     * 유저 삭제
     */
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Long> deleteUser(@PathVariable("user_id") Long id) {
        Long deletedUserId = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUserId);
    }
}
