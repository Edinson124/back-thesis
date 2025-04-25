package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.GlobalVariableDTO;
import com.yawarSoft.Core.Entities.GlobalVariablesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalVariableMapper {

    GlobalVariableDTO toDTO(GlobalVariablesEntity globalVariable);
}
