package com.ecommerce.users.services;

import com.ecommerce.users.exceptions.RoleExistsException;
import com.ecommerce.users.exceptions.RoleNotFoundException;
import com.ecommerce.users.model.Role;
import com.ecommerce.users.repository.IRoleRepository;
import com.ecommerce.users.requests.RoleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository iRoleRepository;

    private final String rolePrefix = "ROLE_";

    /******************************************************************************************************************
     *                                      FIND ROLE BY NAME
     ******************************************************************************************************************/
    @Override
    public Role findRoleByName(String name) {
        return iRoleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("No se encontro ROLE : " + name));
    }

    /******************************************************************************************************************
     *                                      EXISTS ROLE BY NAME
     ******************************************************************************************************************/
    @Override
    public Boolean existsRoleByName(String name) {
        return iRoleRepository.existsRoleByName(name);
    }

    /******************************************************************************************************************
     *                                     CREATE A NEW ROLE
     ******************************************************************************************************************/

    @Override
    public Role createNewRole(RoleRequest roleRequest) {
        roleRequest.setName(formatRolename(roleRequest.getName()));
        log.info("Comprobando si el " + roleRequest.getName() + " ya existe");
        Boolean existsRole = iRoleRepository.existsRoleByName(roleRequest.getName());
        if (existsRole) throw new RoleExistsException("El " + roleRequest.getName() + " ya existe");
        var roleToSave = Role.builder().name(roleRequest.getName()).build();
        log.info("Guardando " + roleRequest.getName());
        Role role = iRoleRepository.save(roleToSave);
        log.info("El " + role.getName() + " ha sido guardado");
        return role;
    }

    /******************************************************************************************************************
     *                                     CREATE A NEW ROLE
     ******************************************************************************************************************/

    @Override
    public Role updateRole(UUID roleId, RoleRequest roleRequest) {
        log.info("Comprobando si el ROLE existe");
        var rolenameFormatted = formatRolename(roleRequest.getName());
        Boolean roleExists = iRoleRepository.existsRoleByName(rolenameFormatted);
        if (roleExists) throw new RoleExistsException("El " + roleRequest.getName().toUpperCase() + " ya existe");
        Role role = iRoleRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException("El ROLE con id: " + roleId + " no se ha encontrado"));
        var rolename = role.getName();
        role.setName(formatRolename(roleRequest.getName()));
        log.info("Actualizando  ROLE");
        var roleSaved = iRoleRepository.save(role);
        log.info("Antes: " + rolename + ", ahora: " + roleSaved.getName());
        log.info("ROLE actualizado correctamente");
        return roleSaved;
    }


    /******************************************************************************************************************
     *                                     PRIVATE METHODS
     ******************************************************************************************************************/

    private String formatRolename(String rolename) {
        rolename = rolename.toUpperCase();
        if (!rolename.contains(rolePrefix))
            rolename = rolePrefix + rolename.toUpperCase();
        return rolename;
    }
}
