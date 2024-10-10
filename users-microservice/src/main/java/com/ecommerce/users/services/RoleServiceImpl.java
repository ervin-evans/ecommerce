package com.ecommerce.users.services;

import com.ecommerce.users.exceptions.RoleExistsException;
import com.ecommerce.users.exceptions.RoleNotFoundException;
import com.ecommerce.users.model.Role;
import com.ecommerce.users.repository.IRoleRepository;
import com.ecommerce.users.requests.RoleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository iRoleRepository;

    private final String rolePrefix = "ROLE_";

    /******************************************************************************************************************
     *                                      FIND ROLE BY PAGE
     ******************************************************************************************************************/
    @Override
    public Page<Role> findRolesByPage(Pageable pageable) {
        log.info("Buscando ROLES por pagina");
        return iRoleRepository.findAll(pageable);
    }

    /******************************************************************************************************************
     *                                      FIND ROLE BY ID
     ******************************************************************************************************************/
    @Override
    public Role findById(UUID roleId) {
        log.info("Buscando ROLE con id: " + roleId);
        return iRoleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("El ROL con el " + roleId + " no existe"));
    }

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
     *                                          DELETE ROLE
     ******************************************************************************************************************/
    @Override
    public Role deleteRole(UUID roleId) {
        log.info("Buscando el ROLE con id:" + roleId + " para eliminar");
        Role role = iRoleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("El ROLE con id " + roleId + " no existe"));
        log.info("ROLE encontrado");
        log.info("Eliminando ROLE");
        iRoleRepository.deleteById(roleId);
        log.info("El " + role.getName() + " ha sido eliminado");
        return role;
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
