package com.streamnz.practisea.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.streamnz.practisea.model.po.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:14
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserVO {

    private Long id;
    private String username;
    private String email;
    private Integer score;
    private String walletAddress;
    private String walletType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bindTime;

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.score = user.getScore();
        this.walletAddress = user.getWalletAddress();
        this.walletType = user.getWalletType();
        this.bindTime = user.getBindTime();
    }


}
