package com.ecommerce.users.services;

import com.ecommerce.users.exceptions.UserNotFoundException;
import com.ecommerce.users.model.User;
import com.ecommerce.users.repository.IUserRepository;
import com.ecommerce.users.requests.UserRequest;
import com.ecommerce.users.requests.UserRequestToUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     *                                             FIND ALL USERS BY PAGE
     ******************************************************************************************************************/
    @Override
    public Page<User> findByPage(Pageable pageable) {
        log.info("Recuperando los usuarios de la pagina " + pageable.getPageNumber());
        return iUserRepository.findAll(pageable);

    }

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
     *                                             FIND USER BY EMAIL
     ******************************************************************************************************************/
    @Override
    public User findByEmail(String email) {
        log.info("Intentando buscar usuario por email");
        User user = iUserRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        log.info("El usuario con email " + user.getEmail() + " ha sido encontrado");
        return user;
    }

    /******************************************************************************************************************
     *                                             EXISTS USER BY EMAIL
     ******************************************************************************************************************/
    @Override
    public Boolean existsUserByEmail(String email) {
        log.info("Verificando si el usuario con email: " + email + " existe");
        boolean exists = iUserRepository.existsByEmail(email);
        if (exists) log.info("El usuario con email: " + email + " si existe");
        else log.info("El usuario con email: " + email + " no existe");
        return exists;
    }

    /******************************************************************************************************************
     *                                             EXISTS USER BY USERNAME
     ******************************************************************************************************************/
    @Override
    public Boolean existsUserByUsername(String username) {
        log.info("Verificando si el usuario con username: " + username + " existe");
        boolean exists = iUserRepository.existsByUsername(username);
        if (exists) log.info("El usuario con username: " + username + " si existe");
        else log.info("El usuario con username: " + username + " no existe");
        return exists;
    }

    /******************************************************************************************************************
     *                                              CREATE USER
     ******************************************************************************************************************/
    @Override
    public User createUser(UserRequest userRequest) {
        log.info("Verificando si el e-mail o el usuario existen");
        if (iUserRepository.existsByEmail(userRequest.getEmail())) throw new RuntimeException("El E-mail ya existe");
        if (iUserRepository.existsByUsername(userRequest.getUsername()))
            throw new RuntimeException("El username ya existe");

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

    /******************************************************************************************************************
     *                                              DELETE USER BY ID
     ******************************************************************************************************************/
    @Override
    public User deleteUser(UUID userId) {
        log.info("Intentando eliminar al usuario con id " + userId);
        User user = iUserRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        iUserRepository.deleteById(userId);
        log.info("Usuario con id " + userId + " eliminado");
        return user;
    }
}
