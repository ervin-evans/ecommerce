package com.ecommerce.users.controllers;

import com.ecommerce.users.requests.UserRequest;
import com.ecommerce.users.response.Message;
import com.ecommerce.users.response.MessageType;
import com.ecommerce.users.response.UserResponse;
import com.ecommerce.users.services.IUserService;
import jakarta.validation.Valid;
import com.ecommerce.users.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private IUserService iUserService;

    //private final Logger logger = LogManager.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<UserResponse> createNewUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Request recibido para crear un usuario");
        User userSaved = iUserService.createUser(userRequest);
        userSaved.setPassword("");
        var message = Message.builder()
                .message("El usuario " + userSaved.getUsername() + " ha sido creado")
                .type(MessageType.INFO).build();
        var userResponse = UserResponse.builder()
                .user(userSaved).message(message).build();
        log.info("Request para crear usuario ha terminado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

    }
}
