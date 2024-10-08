package com.ecommerce.users.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("EL usuario con el id " + userId + " no existe");
    }
}
