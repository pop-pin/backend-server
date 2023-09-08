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
        assertEquals(user, userService.getUser(userId).get());
    }

    @Test
    public void 유저_업데이트() throws Exception {
        // given
        User user1 = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .phone("01012345678")
                .age(24)
                .profileImageUrl("123.123")
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        User user2 = User.builder()
                .name("hs")
                .gender(Gender.valueOf("MAN"))
                .phone("01012347777")
                .age(24)
                .profileImageUrl("123.124")
                .email("7777@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();
        // when
        Long saveId = userService.saveUser(user1);
        // then
        try {
            userService.updateUser(saveId, user2);
        } catch (IllegalStateException e) {
            fail("업데이트 실패");
        }
    }
    
    @Test
    public void 유저_삭제() throws Exception {
        // given
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .phone("01012345678")
                .age(24)
                .profileImageUrl("123.123")
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();
        // when
        Long saveId = userService.saveUser(user);
        userService.deleteUser(saveId);
        // then
        if (!userService.getUser(saveId).isEmpty()) {
            fail("삭제 실패");
        }
    }

}