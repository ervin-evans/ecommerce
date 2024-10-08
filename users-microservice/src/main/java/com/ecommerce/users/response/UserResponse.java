package com.ecommerce.users.response;

import lombok.*;
import com.ecommerce.users.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private User user;
    private Message message;
}
