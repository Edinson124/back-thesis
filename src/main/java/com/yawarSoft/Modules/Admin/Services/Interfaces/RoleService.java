package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Core.Entities.RoleEntity;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    List<RoleDTO> getRolesByBloodBankTypeId(Integer bloodBankTypeId);
    List<RoleEntity> getRolesByIds( Set<Integer> roleIds);
}
