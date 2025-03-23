package com.yawarSoft.Services.Implementations;

import com.yawarSoft.Dto.RoleDTO;
import com.yawarSoft.Entities.RoleEntity;
import com.yawarSoft.Mappers.RoleMapper;
import com.yawarSoft.Repositories.RoleRepository;
import com.yawarSoft.Services.Interfaces.RoleService;
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
