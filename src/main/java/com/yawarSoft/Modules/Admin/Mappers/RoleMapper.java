package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Core.Entities.PermissionEntity;
import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleListDTO;
import com.yawarSoft.Core.Entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleListDTO toListDTO(RoleEntity roleEntity);

    @Mapping(source = "permissionList", target = "permissionList", qualifiedByName = "mapPermissionsToIds")
    RoleDTO toDto(RoleEntity roleEntity);

    @Named("mapPermissionsToIds")
    default Set<Integer> mapPermissionsToIds(Set<PermissionEntity> permissions) {
        if (permissions == null) {
            return null;
        }
        return permissions.stream()
                .map(PermissionEntity::getId)
                .collect(Collectors.toSet());
    }

    @Mapping(source = "permissionList", target = "permissionList", qualifiedByName = "mapIdsToPermissions")
    RoleEntity toEntity(RoleDTO roleDto);

    @Named("mapIdsToPermissions")
    default Set<PermissionEntity> mapIdsToPermissions(Set<Integer> permissionIds) {
        if (permissionIds == null) {
            return null;
        }
        return permissionIds.stream()
                .map(id -> {
                    PermissionEntity permission = new PermissionEntity();
                    permission.setId(id);
                    return permission;
                })
                .collect(Collectors.toSet());
    }
}
