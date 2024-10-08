package com.ecommerce.users.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("EL usuario con el id " + userId + " no existe");
    }
}
