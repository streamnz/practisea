package com.streamnz.practisea.service;

import com.streamnz.practisea.model.dto.UserCreateRequest;
import com.streamnz.practisea.model.dto.UserSearchRequest;
import com.streamnz.practisea.model.dto.UserUpdateWalletRequest;
import com.streamnz.practisea.model.vo.UserPageSearchVO;
import com.streamnz.practisea.model.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:18
 */
public interface UserService {


    /**
     * Query user detail by userId
     * @param userId
     * @return
     */
    UserVO queryUserDetail(Long userId);

    /**
     * Create a new user
     * @param request
     * @return
     */
    UserVO createUser(@Valid UserCreateRequest request);

    /**
     * Update user wallet information
     * @param request
     * @return
     */
    UserVO updateUserWallet(@Valid UserUpdateWalletRequest request);

    /**
     * Paginated conditional query for users
     * @param request
     * @return
     */
    UserPageSearchVO pageConditionQuery(@Valid UserSearchRequest request);
}
