package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Core.Entities.PermissionEntity;
import com.yawarSoft.Modules.Admin.Dto.PermissionDTO;
import com.yawarSoft.Modules.Admin.Mappers.PermissionMapper;
import com.yawarSoft.Modules.Admin.Repositories.PermissionRepository;
import com.yawarSoft.Modules.Admin.Services.Interfaces.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<PermissionDTO> getAllPermissions() {
        List<PermissionEntity> permissionEntities = permissionRepository.findAll();
        return permissionEntities.stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }
}
