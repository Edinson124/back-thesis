package com.yawarSoft.Modules.Transfusion.Mappers;

import com.yawarSoft.Core.Entities.TransfusionResultEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionResultRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransfusionResultMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    TransfusionResultDTO toDetailDTO(TransfusionResultEntity entity);

    @Mapping(source = "transfusionDoctorName", target = "transfusionByName")
    @Mapping(source = "transfusionDoctorLicenseNumber", target = "transfusionByLicenseNumber")
    TransfusionResultEntity toEntityByRequest(TransfusionResultRequestDTO request);

    void updateEntityFromRequest(TransfusionResultRequestDTO request, @MappingTarget TransfusionResultEntity entity);

}
