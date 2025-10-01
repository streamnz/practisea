package com.streamnz.practisea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamnz.practisea.model.dto.UserSearchRequest;
import com.streamnz.practisea.model.po.User;
import com.streamnz.practisea.model.vo.UserPageSearchVO;
import com.streamnz.practisea.model.vo.UserVO;
import com.streamnz.practisea.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TDD Unit Tests for User Search API
 * 展示TDD开发思路：先写测试，再写实现
 * 
 * @Author cheng hao
 * @Date 26/09/2025 21:08
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("用户搜索接口 TDD 测试")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new com.streamnz.practisea.Exception.GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Given 有效搜索条件, When 搜索用户, Then 返回分页结果")
    void givenValidSearchRequest_whenSearchUsers_thenReturnPaginatedResults() throws Exception {
        // Given - 准备测试数据
        UserSearchRequest request = createSearchRequest();
        UserPageSearchVO expectedResult = createPageResult();
        when(userService.pageConditionQuery(any(UserSearchRequest.class))).thenReturn(expectedResult);

        // When & Then - 执行测试并验证结果
        mockMvc.perform(post("/api/v1/users/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.currentPage").value(0));

        // 验证服务层方法被调用
        verify(userService, times(1)).pageConditionQuery(any(UserSearchRequest.class));
    }

    @Test
    @DisplayName("Given 空搜索条件, When 搜索用户, Then 返回默认分页结果")
    void givenEmptySearchRequest_whenSearchUsers_thenReturnDefaultResults() throws Exception {
        // Given
        UserSearchRequest request = new UserSearchRequest();
        UserPageSearchVO expectedResult = createEmptyPageResult();
        when(userService.pageConditionQuery(any(UserSearchRequest.class))).thenReturn(expectedResult);

        // When & Then
        mockMvc.perform(post("/api/v1/users/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.totalElements").value(0));

        verify(userService, times(1)).pageConditionQuery(any(UserSearchRequest.class));
    }


    // 测试数据构建方法
    private UserSearchRequest createSearchRequest() {
        UserSearchRequest request = new UserSearchRequest();
        request.setUsername("testuser");
        request.setPageNumber(0);
        request.setPageSize(10);
        request.setSortBy("id");
        request.setSortOrder("asc");
        return request;
    }

    private UserPageSearchVO createPageResult() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setScore(100);
        user.setBindTime(LocalDateTime.now().minusDays(1));

        List<User> users = Collections.singletonList(user);
        Page<User> page = new PageImpl<>(users, PageRequest.of(0, 10), 1);
        return new UserPageSearchVO(page);
    }

    private UserPageSearchVO createEmptyPageResult() {
        Page<User> page = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        return new UserPageSearchVO(page);
    }
}
