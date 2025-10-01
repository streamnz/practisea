package com.streamnz.practisea.service;

import com.streamnz.practisea.dao.UserRepository;
import com.streamnz.practisea.model.dto.UserSearchRequest;
import com.streamnz.practisea.model.po.User;
import com.streamnz.practisea.model.vo.UserPageSearchVO;
import com.streamnz.practisea.model.vo.UserVO;
import com.streamnz.practisea.service.impl.UserServiceImpl;
import com.streamnz.practisea.uitl.PasswordUtil;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * UserService TDD 单元测试
 * 展示服务层业务逻辑的测试驱动开发
 * 
 * @Author cheng hao
 * @Date 26/09/2025 21:08
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("用户服务层 TDD 测试")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordUtil passwordUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Given 有效用户ID, When 查询用户详情, Then 返回用户信息")
    void givenValidUserId_whenQueryUserDetail_thenReturnUserInfo() {
        // Given
        Long userId = 1L;
        User mockUser = createMockUser();
        when(userRepository.queryUserDetailById(userId)).thenReturn(mockUser);

        // When
        UserVO result = userService.queryUserDetail(userId);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        
        verify(userRepository, times(1)).queryUserDetailById(userId);
    }

    @Test
    @DisplayName("Given 空用户ID, When 查询用户详情, Then 抛出异常")
    void givenNullUserId_whenQueryUserDetail_thenThrowException() {
        // Given
        Long userId = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            userService.queryUserDetail(userId);
        });

        verify(userRepository, never()).queryUserDetailById(anyLong());
    }

    @Test
    @DisplayName("Given 不存在的用户ID, When 查询用户详情, Then 抛出异常")
    void givenNonExistentUserId_whenQueryUserDetail_thenThrowException() {
        // Given
        Long userId = 999L;
        when(userRepository.queryUserDetailById(userId)).thenReturn(null);

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            userService.queryUserDetail(userId);
        });

        verify(userRepository, times(1)).queryUserDetailById(userId);
    }

    @Test
    @DisplayName("Given 有效搜索条件, When 分页查询用户, Then 返回分页结果")
    void givenValidSearchRequest_whenPageConditionQuery_thenReturnPaginatedResults() {
        // Given
        UserSearchRequest request = createSearchRequest();
        User mockUser = createMockUser();
        Page<User> mockPage = new PageImpl<>(Collections.singletonList(mockUser), PageRequest.of(0, 10), 1);
        
        when(userRepository.searchUsersWithPagination(
                any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(mockPage);

        // When
        UserPageSearchVO result = userService.pageConditionQuery(request);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getCurrentPage());
        assertEquals(1, result.getUsers().size());
        
        verify(userRepository, times(1)).searchUsersWithPagination(
                any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    // 测试数据构建方法
    private User createMockUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setScore(100);
        user.setWalletAddress("0x1234567890abcdef");
        user.setWalletType("ETH");
        user.setBindTime(LocalDateTime.now().minusDays(1));
        return user;
    }

    private UserSearchRequest createSearchRequest() {
        UserSearchRequest request = new UserSearchRequest();
        request.setUsername("testuser");
        request.setPageNumber(0);
        request.setPageSize(10);
        request.setSortBy("id");
        request.setSortOrder("asc");
        return request;
    }
}
