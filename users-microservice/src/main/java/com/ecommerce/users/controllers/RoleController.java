package com.ecommerce.users.controllers;

import com.ecommerce.users.model.Role;
import com.ecommerce.users.requests.RoleRequest;
import com.ecommerce.users.response.Message;
import com.ecommerce.users.response.MessageType;
import com.ecommerce.users.response.RoleCustomResponse;
import com.ecommerce.users.response.RoleResponse;
import com.ecommerce.users.services.IRoleService;
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
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    @Value("${ecommerce.microservice.role.page-size}")
    private Integer pageSize;

    /******************************************************************************************************************
     *                                     FIND ROLE BY PAGE
     ******************************************************************************************************************/
    @GetMapping
    public ResponseEntity<RoleCustomResponse> findAllRolesByPage(@RequestParam(defaultValue = "0") int page) {
        log.info("Request para buscar ROLES por pagina iniciado");
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Role> rolesByPage = iRoleService.findRolesByPage(pageable);
        var response = RoleCustomResponse.builder()
                .roles(rolesByPage.getContent())
                .totalPages(rolesByPage.getTotalPages())
                .totalElements(rolesByPage.getTotalElements())
                .numberOfElements(rolesByPage.getNumberOfElements())
                .isEmpty(rolesByPage.isEmpty())
                .isFirst(rolesByPage.isFirst())
                .isLast(rolesByPage.isLast())
                .build();
        log.info("ROLES encontrados: " + response.getTotalElements());
        log.info("Request para buscar ROLES por pagina finalizado");
        return ResponseEntity.ok(response);
    }

    /******************************************************************************************************************
     *                                     FIND ROLE BY ID
     ******************************************************************************************************************/
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponse> findRoleById(@PathVariable UUID roleId) {
        log.info("Request para buscar un ROLE por id iniciado");
        var roleById = iRoleService.findById(roleId);
        var roleResponse = RoleResponse.builder().role(roleById).build();
        log.info("ROLE encontrado");
        log.info("Request para buscar un ROLE por id finalizado con exito");
        return ResponseEntity.ok(roleResponse);
    }

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

    /******************************************************************************************************************
     *                                          UPDATE UN ROLE
     ******************************************************************************************************************/
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable UUID roleId, @Valid @RequestBody RoleRequest roleRequest) {
        log.info("Request para ACTUALIZAR un ROLE iniciado");
        Role roleUpdated = iRoleService.updateRole(roleId, roleRequest);
        var message = Message.builder().message("El ROLE ha sido actualizado").type(MessageType.INFO).build();
        var response = RoleResponse.builder().role(roleUpdated).message(message).build();
        log.info("Request para ACTUALIZAR un ROLE finalizado con exito");
        return ResponseEntity.ok(response);
    }

    /******************************************************************************************************************
     *                                          DELETE ROLE
     ******************************************************************************************************************/
    @DeleteMapping("/{roleId}")
    public ResponseEntity<RoleResponse> deleteRole(@PathVariable UUID roleId) {
        log.info("Request para ELIMINAR un ROLE iniciado");
        Role role = iRoleService.deleteRole(roleId);
        var message = Message.builder().message("El ROLE ha sido elimiando").type(MessageType.INFO).build();
        var response = RoleResponse.builder().role(role).message(message).build();
        log.info("Request para ELIMINAR un ROLE ha finalizado");
        return ResponseEntity.ok(response);
    }

}
