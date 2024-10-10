package com.ecommerce.users.services;

import com.ecommerce.users.model.Role;
import com.ecommerce.users.requests.RoleRequest;

import java.util.UUID;

public interface IRoleService {

    public Role findById(UUID roleId);
    public Role findRoleByName(String name);

    public Boolean existsRoleByName(String name);

    public Role createNewRole(RoleRequest roleRequest);

    public Role updateRole(UUID roleId, RoleRequest roleRequest);
}
