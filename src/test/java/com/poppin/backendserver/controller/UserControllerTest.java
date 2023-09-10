package com.poppin.backendserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poppin.backendserver.dto.UserDTO;
import com.poppin.backendserver.entity.User;
import com.poppin.backendserver.enums.Gender;
import com.poppin.backendserver.enums.UserRole;
import com.poppin.backendserver.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UserService userService;

    @Test
    public void testGetUser() throws Exception {
        Long userId = 1L;
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .phone("01012345678")
                .age(24)
                .profileImageUrl("123.123")
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        when(userService.getUser(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/v1/user/{user_id}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.gender").value(user.getGender().toString()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()))
                .andExpect(jsonPath("$.age").value(user.getAge()))
                .andExpect(jsonPath("$.profileImageUrl").value(user.getProfileImageUrl()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.userRole").value(user.getUserRole().toString()));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .phone("01012345678")
                .age(24)
                .profileImageUrl("123.123")
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        mockMvc.perform(post("/v1/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = User.builder()
                .name("heesang")
                .gender(Gender.valueOf("MAN"))
                .phone("01012345678")
                .age(24)
                .profileImageUrl("123.123")
                .email("1234@gmail.com")
                .userRole(UserRole.valueOf("USER"))
                .build();

        Long saveId = userService.saveUser(user);

        UserDTO userDTO = UserDTO.builder()
                .name("heesang")
                .phone("0101237777")
                .profileImageUrl("123.123")
                .email("1234@gmail.com")
                .build();

        mockMvc.perform(put("/v1/user/{user_id}", saveId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/v1/user/{user_id}", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}