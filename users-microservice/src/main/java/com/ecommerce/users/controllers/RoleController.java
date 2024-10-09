package com.ecommerce.users.controllers;

import com.ecommerce.users.model.Role;
import com.ecommerce.users.requests.RoleRequest;
import com.ecommerce.users.response.Message;
import com.ecommerce.users.response.MessageType;
import com.ecommerce.users.response.RoleResponse;
import com.ecommerce.users.services.IRoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    /******************************************************************************************************************
     *                                     CREATE A NEW ROLE
     ******************************************************************************************************************/
    @PostMapping
    public ResponseEntity<RoleResponse> createNewRole(@Valid @RequestBody RoleRequest roleRequest) {
        log.info("Request recibido para crear un nuevo ROLE");
        Role roleSaved = iRoleService.createNewRole(roleRequest);
        var message = Message.builder()
                .message("El ROLE " + roleSaved.getName() + " ha sido guardado").type(MessageType.INFO).build();
        var roleResponse = RoleResponse.builder()
                .role(roleSaved).message(message).build();
        log.info("Request para crear un nuevo ROLE finalizado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse);
    }
}
