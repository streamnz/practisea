package com.streamnz.practisea.model.vo;

import com.streamnz.practisea.model.po.User;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;

/**
 * @Author cheng hao
 * @Date 29/09/2025 21:26
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPageSearchVO {

    private List<UserVO> users;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int currentSize;
    private boolean first;
    private boolean last;
    private boolean hasNext;
    private boolean hasPrevious;

    public UserPageSearchVO(Page<User> page){

        Objects.requireNonNull(page, "Page must not be null");

        users = page.map(UserVO::new).getContent();
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
        currentPage = page.getNumber();
        currentSize = page.getSize();
        first = page.isFirst();
        last = page.isLast();
        hasNext = page.hasNext();
        hasPrevious = page.hasPrevious();
    }
}
