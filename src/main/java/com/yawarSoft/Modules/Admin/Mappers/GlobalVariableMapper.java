package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Modules.Admin.Dto.GlobalVariableDTO;
import com.yawarSoft.Core.Entities.GlobalVariablesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalVariableMapper {

    GlobalVariableDTO toDTO(GlobalVariablesEntity globalVariable);
}
