package com.ecommerce.users.services;

import com.ecommerce.users.repository.IUserRepository;
import com.ecommerce.users.requests.UserRequest;
import com.ecommerce.users.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    //private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User createUser(UserRequest userRequest) {
        log.info("Intentando crear al usuario "+userRequest.getUsername());
        var userToSave = User.builder()
                .name(userRequest.getName())
                .lastname(userRequest.getLastname())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .image(userRequest.getImage())
                .build();
        User userSaved =  iUserRepository.save(userToSave);
        log.info("EL usuario "+userSaved.getUsername() + " ha sido guardado");
        return userSaved;
    }
}
