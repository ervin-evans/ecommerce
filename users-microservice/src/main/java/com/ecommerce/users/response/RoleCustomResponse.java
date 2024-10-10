package com.ecommerce.users.response;

import com.ecommerce.users.model.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleCustomResponse {
    private List<Role> roles;
    private long totalElements;
    private long totalPages;
    private long numberOfElements;
    private boolean isLast;
    private boolean isFirst;
    private boolean isEmpty;
}
