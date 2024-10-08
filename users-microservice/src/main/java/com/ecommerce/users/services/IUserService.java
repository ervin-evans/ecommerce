package com.ecommerce.users.services;

import com.ecommerce.users.requests.UserRequest;
import com.ecommerce.users.model.User;
import com.ecommerce.users.requests.UserRequestToUpdate;

import java.util.UUID;

public interface IUserService {
    public User findUserById(UUID userId);
    public User createUser(UserRequest userRequest);
    public User updateUser(UUID userId,  UserRequestToUpdate userRequest);
}
