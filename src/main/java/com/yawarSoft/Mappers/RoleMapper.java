package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.RoleDTO;
import com.yawarSoft.Entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleDTO toDTO(RoleEntity roleEntity);
}
