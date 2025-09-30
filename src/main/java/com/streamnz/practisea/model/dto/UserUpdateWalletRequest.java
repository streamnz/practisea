package com.streamnz.practisea.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author cheng hao
 * @Date 26/09/2025 22:38
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateWalletRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotBlank(message = "Wallet address cannot be blank")
    private String walletAddress;
    @NotBlank(message = "Wallet type cannot be blank")
    private String walletType;

}
