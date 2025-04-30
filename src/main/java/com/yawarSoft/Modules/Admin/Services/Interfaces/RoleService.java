package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleListDTO;
import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Modules.Admin.Dto.RoleSelectDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleListDTO> getAllRoles();
    Page<RoleListDTO> getRolesPaginated(int page, int size, String search,String status);
    List<RoleSelectDTO> getRolesSelectActive();
    List<RoleEntity> getRolesByIds( Set<Integer> roleIds);
    RoleDTO getRoleById(Integer id);
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(Integer id,RoleDTO roleDTO);
}
