package com.yawarSoft.Modules.Transfusion.Mappers;

import com.yawarSoft.Core.Entities.TransfusionAssignmentEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionAssignmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransfusionAssignmentMapper {

    @Mapping(source = "bloodUnit.id", target = "bloodUnitId")
    @Mapping(source = "bloodUnit.unitType", target = "bloodUnitType")
    @Mapping(source = "bloodUnit.bloodType", target = "bloodUnitBloodType")
    @Mapping(source = "performedTestBy.id", target = "performedTestById")
    @Mapping(source = "performedTestBy", target = "performedTestByName", qualifiedByName = "getFullName")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    TransfusionAssignmentDTO toTransfusionAssignmentDto(TransfusionAssignmentEntity entity);

    List<TransfusionAssignmentDTO> toTransfusionAssignmentDtoList(List<TransfusionAssignmentEntity> entityList);
}
