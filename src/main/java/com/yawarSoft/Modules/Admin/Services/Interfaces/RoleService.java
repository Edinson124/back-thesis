package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleListDTO;
import com.yawarSoft.Core.Entities.RoleEntity;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleListDTO> getAllRoles();
    List<RoleEntity> getRolesByIds( Set<Integer> roleIds);
    RoleDTO getRoleById(Integer id);
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(Integer id,RoleDTO roleDTO);
}
