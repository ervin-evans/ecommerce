package com.ecommerce.users.services;

import com.ecommerce.users.requests.UserRequest;
import com.ecommerce.users.model.User;

public interface IUserService {
    public User createUser(UserRequest userRequest);
}
