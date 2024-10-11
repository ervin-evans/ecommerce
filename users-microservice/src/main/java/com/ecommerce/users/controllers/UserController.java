package com.ecommerce.users.controllers;

import com.ecommerce.users.model.User;
import com.ecommerce.users.requests.UserRequest;
import com.ecommerce.users.requests.UserRequestToUpdate;
import com.ecommerce.users.response.Message;
import com.ecommerce.users.response.MessageType;
import com.ecommerce.users.response.UserCustomResponse;
import com.ecommerce.users.response.UserResponse;
import com.ecommerce.users.services.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Value("${ecommerce.microservice.role.page-size:10}")
    private int pageSize;
    //private final Logger logger = LogManager.getLogger(UserController.class);

    /******************************************************************************************************************
     *                                             FIND ALL USERS BY PAGE
     ******************************************************************************************************************/
    @GetMapping
    public ResponseEntity<UserCustomResponse> findAllUsersByPage(@RequestParam(defaultValue = "0") int page) {
        log.info("Request para buscar los usuarios por pagina iniciado");
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> usersByPage = iUserService.findByPage(pageable);
        log.info("Pagina: " + usersByPage.getPageable().getPageNumber() + " - Usuarios encontrados: " + usersByPage.getNumberOfElements());
        var response = UserCustomResponse.builder()
                .users(usersByPage.getContent())
                .totalPages(usersByPage.getTotalPages())
                .totalElements(usersByPage.getTotalElements())
                .numberOfElements(usersByPage.getNumberOfElements())
                .isEmpty(usersByPage.isEmpty())
                .isFirst(usersByPage.isFirst())
                .isLast(usersByPage.isLast()).build();
        log.info("Request para buscar los usuarios por pagina finalizado con exito");
        return ResponseEntity.ok(response);
    }

    /******************************************************************************************************************
     *                                             FIND USER BY ID
     ******************************************************************************************************************/
    @GetMapping("/find/id/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable UUID userId) {
        User userById = iUserService.findUserById(userId);
        UserResponse response = UserResponse.builder().user(userById).build();
        return ResponseEntity.ok(response);
    }

    /******************************************************************************************************************
     *                                             FIND USER BY USERNAME
     ******************************************************************************************************************/
    @GetMapping("/find/username/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) {
        log.info("Request recibido para buscar un usuario por username");
        User userByUsername = iUserService.findUserByUsername(username);
        userByUsername.setPassword(null);
        UserResponse response = UserResponse.builder().user(userByUsername).build();
        log.info("Request para buscar usuario por username terminado con exito!");
        return ResponseEntity.ok(response);
    }

    /******************************************************************************************************************
     *                                              CREATE NEW USER
     ******************************************************************************************************************/

    @PostMapping
    public ResponseEntity<UserResponse> createNewUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Request recibido para crear un usuario");
        User userSaved = iUserService.createUser(userRequest);
        userSaved.setPassword(null);
        var message = Message.builder()
                .message("El usuario " + userSaved.getUsername() + " ha sido creado")
                .type(MessageType.INFO).build();
        var userResponse = UserResponse.builder()
                .user(userSaved).message(message).build();
        log.info("Request para crear usuario ha terminado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    /******************************************************************************************************************
     *                                              UPDATE USER
     ******************************************************************************************************************/
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserRequestToUpdate userRequest) {
        log.info("Request recibido para actualizar un usuario");
        var userUpdated = iUserService.updateUser(userId, userRequest);
        var message = Message.builder()
                .message("El usuario con id " + userUpdated.getId() + " ha sido actualizado")
                .type(MessageType.INFO).build();
        var userResponse = UserResponse.builder()
                .user(userUpdated).message(message).build();
        log.info("Request para actualizar al usuario terminado con exito!");
        return ResponseEntity.ok(userResponse);
    }

    /******************************************************************************************************************
     *                                              DELETE USER
     ******************************************************************************************************************/
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("id") UUID userId) {
        log.info("Request para eliminar usuario recibido");
        User user = iUserService.deleteUser(userId);
        var message = Message.builder()
                .message("El usuario con el id " + userId + " ha sido eliminado")
                .type(MessageType.INFO).build();
        var respose = UserResponse.builder()
                .user(user).message(message).build();
        log.info("Request para eliminar usuario ha terminado con exito");
        return ResponseEntity.ok(respose);

    }
}
