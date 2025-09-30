package com.streamnz.practisea.model.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:09
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "score", nullable = true)
    private Integer score;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "wallet_address", nullable = true)
    private String walletAddress;
    @Column(name = "wallet_type", nullable = true)
    private String walletType;
    @Column(name = "bind_time", nullable = true)
    private LocalDateTime bindTime;



}
