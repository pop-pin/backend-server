package com.poppin.backendserver.service;

import com.poppin.backendserver.entity.User;
import com.poppin.backendserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Long saveUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void updateUser(Long id, User updatedUser) {
        Optional<User> existedUser = userRepository.findById(id);

        if (existedUser.isPresent()) {
            User user = existedUser.get();
            user.setName(updatedUser.getName());
            user.setPhone(updatedUser.getPhone());
            user.setProfileImageUrl(updatedUser.getProfileImageUrl());
            user.setEmail(updatedUser.getEmail());
        }
    }

    @Transactional
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
