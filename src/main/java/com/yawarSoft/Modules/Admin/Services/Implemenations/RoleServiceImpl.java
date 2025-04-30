package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Core.Entities.PermissionEntity;
import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleListDTO;
import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Modules.Admin.Dto.RoleSelectDTO;
import com.yawarSoft.Modules.Admin.Enums.RoleStatus;
import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import com.yawarSoft.Modules.Admin.Mappers.RoleMapper;
import com.yawarSoft.Modules.Admin.Repositories.RoleRepository;
import com.yawarSoft.Modules.Admin.Services.Interfaces.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<RoleListDTO> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RoleListDTO> getRolesPaginated(int page, int size, String search,String status) {
        Pageable pageable = PageRequest.of(page, size);
        search = (search != null && !search.isBlank()) ? search : null;
        status = (status != null && !status.isBlank()) ? status : null;
        return roleRepository.findByFilters(search, status, pageable)
                .map(roleMapper::toListDTO);
    }

    @Override
    public List<RoleSelectDTO> getRolesSelectActive() {
        List<RoleEntity> roles = roleRepository.findByStatus(RoleStatus.ACTIVE.name());
        return roles.stream()
                .map(roleMapper::toSelectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleEntity> getRolesByIds(Set<Integer> roleIds) {
        return roleRepository.findAllById(roleIds);
    }

    @Override
    public RoleDTO getRoleById(Integer id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto)
                .orElse(null);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        RoleEntity roleEntity = roleMapper.toEntity(roleDTO);
        if (roleRepository.existsByName(roleEntity.getName())) {
            throw new IllegalArgumentException("El nombre del rol ya está registrado.");
        }
        roleEntity.setStatus(RoleStatus.ACTIVE.name());
        RoleEntity savedRole = roleRepository.save(roleEntity);
        roleDTO.setId(savedRole.getId());
        return roleDTO;
    }

    @Override
    public RoleDTO updateRole(Integer id, RoleDTO roleDTO) {
        RoleEntity existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + id));

        if (!existingRole.getName().equals(roleDTO.getName()) &&
                roleRepository.existsByName(roleDTO.getName())) {
            throw new IllegalArgumentException("El nombre del rol ya está registrado.");
        }

        existingRole.setName(roleDTO.getName());
        existingRole.setDescription(roleDTO.getDescription());

        Set<PermissionEntity> updatedPermissions = roleDTO.getPermissionList().stream()
                .map(permissionId -> {
                    PermissionEntity permission = new PermissionEntity();
                    permission.setId(permissionId);
                    return permission;
                })
                .collect(Collectors.toSet());

        existingRole.setPermissionList(updatedPermissions);
        RoleEntity savedRole = roleRepository.save(existingRole);
        roleDTO.setId(savedRole.getId());
        return roleDTO;
    }

}
