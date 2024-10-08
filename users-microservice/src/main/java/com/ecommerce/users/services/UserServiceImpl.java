package com.ecommerce.users.services;

import com.ecommerce.users.exceptions.UserNotFoundException;
import com.ecommerce.users.model.User;
import com.ecommerce.users.repository.IUserRepository;
import com.ecommerce.users.requests.UserRequest;
import com.ecommerce.users.requests.UserRequestToUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    //private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder encoder;

    /******************************************************************************************************************
     *                                             FIND USER BY ID
     ******************************************************************************************************************/
    @Override
    public User findUserById(UUID userId) {
        return iUserRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    /******************************************************************************************************************
     *                                             FIND USER BY USERNAME
     ******************************************************************************************************************/
    @Override
    public User findUserByUsername(String username) {
        return iUserRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    /******************************************************************************************************************
     *                                              CREATE USER
     ******************************************************************************************************************/
    @Override
    public User createUser(UserRequest userRequest) {
        log.info("Intentando crear al usuario " + userRequest.getUsername());
        var userToSave = User.builder()
                .name(userRequest.getName())
                .lastname(userRequest.getLastname())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .image(userRequest.getImage())
                .build();
        User userSaved = iUserRepository.save(userToSave);
        log.info("EL usuario " + userSaved.getUsername() + " ha sido guardado");
        return userSaved;
    }

    /******************************************************************************************************************
     *                                              UPDATE USER
     ******************************************************************************************************************/
    @Override
    public User updateUser(UUID userId, UserRequestToUpdate userRequest) {
        log.info("Intentando actualizar al usuario con id: " + userId);
        var user = iUserRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setName(userRequest.getName());
        user.setLastname(userRequest.getLastname());
        User userUpdated = iUserRepository.save(user);
        log.info("El usuario con id: " + user.getId() + " ha sido actualizado");
        return userUpdated;
    }
}
