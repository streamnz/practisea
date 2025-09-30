package com.streamnz.practisea.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author cheng hao
 * @Date 29/09/2025 20:35
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSearchRequest {

    private String username;
    private String email;
    // Score range 校验必须有开头有结尾
    private Integer scoreStart;
    private Integer scoreEnd;
    private String walletAddress;
    private String walletType;
    // Bind time range  校验必须有开头有结尾
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bindTimeStart;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bindTimeEnd;

    private String sortBy = "id";
    private String sortOrder = "asc"; // or "desc"
    private Integer pageNumber = 0; // default to first page
    private Integer pageSize = 10; // default page size

    @JsonIgnore
    public Boolean isValidScoreRange() {
        if ((scoreStart == null && scoreEnd != null) || (scoreStart != null && scoreEnd == null)) {
            return false;
        }
        if (scoreStart != null && scoreEnd != null && scoreStart > scoreEnd) {
            return false;
        }
        return true;
    }

    public Boolean isValidBindTimeRange() {
        if ((bindTimeStart == null && bindTimeEnd != null) || (bindTimeStart != null && bindTimeEnd == null)) {
            return false;
        }
        if (bindTimeStart != null && bindTimeEnd != null && bindTimeStart.isAfter(bindTimeEnd)) {
            return false;
        }
        return true;
    }
}
