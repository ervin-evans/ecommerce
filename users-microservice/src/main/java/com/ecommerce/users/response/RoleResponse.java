package com.ecommerce.users.response;

import com.ecommerce.users.model.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private Role role;
    private Message message;
}
