package com.yawarSoft.Services.Interfaces;

import com.yawarSoft.Dto.RoleDTO;
import com.yawarSoft.Entities.RoleEntity;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    List<RoleDTO> getRolesByBloodBankTypeId(Integer bloodBankTypeId);
    List<RoleEntity> getRolesByIds( Set<Integer> roleIds);
}
