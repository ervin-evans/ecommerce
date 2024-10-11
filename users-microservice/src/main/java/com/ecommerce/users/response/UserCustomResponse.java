package com.ecommerce.users.response;

import com.ecommerce.users.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCustomResponse {
    private List<User> users;
    private long totalElements;
    private long totalPages;
    private long numberOfElements;
    private boolean isLast;
    private boolean isFirst;
    private boolean isEmpty;
}
