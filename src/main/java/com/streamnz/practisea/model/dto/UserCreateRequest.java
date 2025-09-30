package com.streamnz.practisea.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:44
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username can only contain letters, numbers, underscores and dashes")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(message = "Password must contain at least one uppercase letter, one lowercase letter, one number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid")
    @Size(max = 255, message = "Email must be less than 255 characters")
    private String email;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
    @NotNull(message = "Bind time cannot be null")
    @Past(message = "Bind time must be in the past")
    private LocalDateTime bindTime;
}
