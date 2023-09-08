package com.poppin.backendserver.service;

import com.poppin.backendserver.entity.User;
import com.poppin.backendserver.enums.Gender;
import com.poppin.backendserver.enums.UserRole;
import com.poppin.backendserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 유저생성() throws Exception {
        //given
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .phone("01012345678")
                .age(24)
                .profileImageUrl("123.123")
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        //when
        Long userId = userService.saveUser(user);

        //then
        assertEquals(user, userService.getUser(userId));
    }

}