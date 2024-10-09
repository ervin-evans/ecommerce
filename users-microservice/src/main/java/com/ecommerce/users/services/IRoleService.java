package com.ecommerce.users.services;

import com.ecommerce.users.model.Role;
import com.ecommerce.users.requests.RoleRequest;

public interface IRoleService {
    public Role findRoleByName(String name);
    public Boolean existsRoleByName(String name);
    public Role createNewRole(RoleRequest roleRequest);
}
