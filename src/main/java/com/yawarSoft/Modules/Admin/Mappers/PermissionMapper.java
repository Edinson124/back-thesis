package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Core.Entities.PermissionEntity;
import com.yawarSoft.Modules.Admin.Dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    PermissionDTO toDto(PermissionEntity permission);
}
