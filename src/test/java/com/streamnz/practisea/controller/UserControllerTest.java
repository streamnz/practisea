package com.streamnz.practisea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamnz.practisea.model.dto.UserCreateRequest;
import com.streamnz.practisea.model.dto.UserUpdateWalletRequest;
import com.streamnz.practisea.model.vo.UserVO;
import com.streamnz.practisea.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @Author cheng hao
 * @Date 26/09/2025 21:08
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryUserDetail_Success() throws Exception {
        // Given
        Long userId = 1L;
        UserVO expectedUser = createTestUserVO();
        when(userService.queryUserDetail(userId)).thenReturn(expectedUser);

        // When & Then
        mockMvc.perform(get("/api/v1/users/detail")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void queryUserDetail_InvalidUserId() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/users/detail")
                        .param("userId", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_Success() throws Exception {
        // Given
        UserCreateRequest request = createTestUserCreateRequest();
        UserVO expectedUser = createTestUserVO();
        when(userService.createUser(any(UserCreateRequest.class))).thenReturn(expectedUser);

        // When & Then
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void createUser_InvalidRequest() throws Exception {
        // Given
        UserCreateRequest invalidRequest = new UserCreateRequest();
        invalidRequest.setUsername(""); // Invalid: empty username
        invalidRequest.setEmail("invalid-email"); // Invalid: wrong email format
        invalidRequest.setPassword("123"); // Invalid: too short password

        // When & Then
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_Success() throws Exception {
        // Given
        UserUpdateWalletRequest request = createTestUserUpdateRequest();
        UserVO expectedUser = createTestUserVO();
        when(userService.updateUserWallet(any(UserUpdateWalletRequest.class))).thenReturn(expectedUser);

        // When & Then
        mockMvc.perform(put("/api/v1/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.walletAddress").value("0x1234567890abcdef"));
    }


    // Helper methods
    private UserVO createTestUserVO() {
        UserVO userVO = new UserVO();
        userVO.setId(1L);
        userVO.setUsername("testuser");
        userVO.setEmail("test@example.com");
        userVO.setScore(100);
        userVO.setWalletAddress("0x1234567890abcdef");
        userVO.setWalletType("ETH");
        userVO.setBindTime(LocalDateTime.now());
        return userVO;
    }

    private UserCreateRequest createTestUserCreateRequest() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("Test123456");
        request.setBindTime(LocalDateTime.now());
        return request;
    }

    private UserUpdateWalletRequest createTestUserUpdateRequest() {
        UserUpdateWalletRequest request = new UserUpdateWalletRequest();
        request.setUserId(1L);
        request.setWalletAddress("0x1234567890abcdef");
        request.setWalletType("ETH");
        return request;
    }
}
