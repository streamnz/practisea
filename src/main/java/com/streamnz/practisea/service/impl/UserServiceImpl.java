package com.streamnz.practisea.service.impl;

import com.streamnz.practisea.dao.UserRepository;
import com.streamnz.practisea.model.dto.UserCreateRequest;
import com.streamnz.practisea.model.dto.UserSearchRequest;
import com.streamnz.practisea.model.dto.UserUpdateWalletRequest;
import com.streamnz.practisea.model.po.User;
import com.streamnz.practisea.model.vo.UserPageSearchVO;
import com.streamnz.practisea.model.vo.UserVO;
import com.streamnz.practisea.service.UserService;
import com.streamnz.practisea.uitl.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordUtil passwordUtil;


    @Override
    public UserVO queryUserDetail(Long userId) {
        Objects.requireNonNull(userId, "userId must not be null");
        User user = userRepository.queryUserDetailById(userId);
        Objects.requireNonNull(user, "User not found");
        return new UserVO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO createUser(UserCreateRequest request) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setBindTime(request.getBindTime());
        user.setPassword(passwordUtil.encodePassword(request.getPassword()));
        user = userRepository.save(user);
        Objects.requireNonNull(user, "User not created");
        return new UserVO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO updateUserWallet(UserUpdateWalletRequest request) {
        User user = userRepository.queryUserDetailById(request.getUserId());
        Objects.requireNonNull(user, "User not found");
        user.setWalletAddress(request.getWalletAddress());
        user.setWalletType(request.getWalletType());
        user = userRepository.save(user);
        Objects.requireNonNull(user, "User not updated");
        return new UserVO(user);
    }

    @Override
    public UserPageSearchVO pageConditionQuery(UserSearchRequest request) {
        Sort sort = Sort.by("asc".equalsIgnoreCase(request.getSortOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        Page<User> users = userRepository.searchUsersWithPagination(request.getUsername(),
                request.getEmail(),
                request.getWalletAddress(),
                request.getWalletType(),
                request.getScoreStart(),
                request.getScoreEnd(),
                request.getBindTimeStart(),
                request.getBindTimeEnd(),
                pageable);
        // Placeholder implementation for search functionality
        return new UserPageSearchVO(users);
    }
}
