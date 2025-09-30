package com.streamnz.practisea.controller;

import com.streamnz.practisea.model.dto.UserCreateRequest;
import com.streamnz.practisea.model.dto.UserSearchRequest;
import com.streamnz.practisea.model.dto.UserUpdateWalletRequest;
import com.streamnz.practisea.model.vo.UserPageSearchVO;
import com.streamnz.practisea.model.vo.UserVO;
import com.streamnz.practisea.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<UserVO> queryUserDetail(@PathVariable("userId")
                                                  @NotNull(message = "User ID cannot be null")
                                                  Long userId) {
        UserVO userVO = userService.queryUserDetail(userId);
        return ResponseEntity.ok(userVO);
    }

    @PostMapping("/create")
    public ResponseEntity<UserVO> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserVO userVO = userService.createUser(request);
        return ResponseEntity.ok(userVO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserVO> updateUser(@RequestBody @Valid UserUpdateWalletRequest request) {
        UserVO userVO = userService.updateUserWallet(request);
        return ResponseEntity.ok(userVO);
    }

    @PostMapping("/search")
    public UserPageSearchVO searchUsers(@RequestBody @Valid UserSearchRequest request) {
        return userService.pageConditionQuery(request);
    }
}
