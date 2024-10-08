package com.ecommerce.users.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("EL usuario no existe");
    }
}
