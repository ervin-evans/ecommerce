package com.ecommerce.users.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String message;
    private MessageType type;
}
