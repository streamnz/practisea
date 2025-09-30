package com.streamnz.practisea.dao;

import com.streamnz.practisea.model.dto.UserSearchRequest;
import com.streamnz.practisea.model.po.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:17
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User queryUserDetailById(Long userId);


    @Query("SELECT u FROM User u " +
            "WHERE (:username IS NULL OR u.username = :username) " +
            "AND (:email IS NULL OR u.email = :email) " +
            "AND (:walletAddress IS NULL OR u.walletAddress = :walletAddress) " +
            "AND (:walletType IS NULL OR u.walletType = :walletType) " +
            "AND (:minScore IS NULL OR u.score >= :minScore) " +
            "AND (:maxScore IS NULL OR u.score <= :maxScore) " +
            "AND (:bindTimeStart IS NULL OR u.bindTime >= :bindTimeStart) " +
            "AND (:bindTimeEnd IS NULL OR u.bindTime <= :bindTimeEnd)")
    Page<User> searchUsersWithPagination(@Param("username") String username,
                                         @Param("email") String email,
                                         @Param("walletAddress") String walletAddress,
                                         @Param("walletType") String walletType,
                                         @Param("minScore") Integer minScore,
                                         @Param("maxScore") Integer maxScore,
                                         @Param("bindTimeStart") LocalDateTime bindTimeStart,
                                         @Param("bindTimeEnd") LocalDateTime bindTimeEnd,
                                         Pageable pageable);



    boolean existsByUsername(@NotBlank(message = "Username cannot be blank")
                             @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
                             @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username can only contain letters, numbers, underscores and dashes")
                             String username);

    boolean existsByEmail(@NotBlank(message = "Email cannot be blank")
                          @Email(message = "Email is not valid")
                          @Size(max = 255, message = "Email must be less than 255 characters")
                          String email);
}
