package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Core.Entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    @Mapping(source = "bloodBankType.name", target = "typeBloodBank")
    RoleDTO toDTO(RoleEntity roleEntity);
}
