package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Modules.Admin.Mappers.RoleMapper;
import com.yawarSoft.Modules.Admin.Repositories.RoleRepository;
import com.yawarSoft.Modules.Admin.Services.Interfaces.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> getRolesByBloodBankTypeId(Integer bloodBankTypeId) {
        List<RoleEntity> roles = roleRepository.findRolesByBloodBankTypeId(bloodBankTypeId);
        return roles.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleEntity> getRolesByIds(Set<Integer> roleIds) {
        return roleRepository.findAllById(roleIds);
    }
}
