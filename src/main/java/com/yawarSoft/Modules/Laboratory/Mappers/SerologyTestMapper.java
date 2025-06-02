package com.yawarSoft.Modules.Laboratory.Mappers;

import com.yawarSoft.Core.Entities.SerologyTestEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Laboratory.Dto.Request.SerologyTestRequest;
import com.yawarSoft.Modules.Laboratory.Dto.SerologyTestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SerologyTestMapper {


    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    SerologyTestDTO toDto(SerologyTestEntity entity);

    SerologyTestEntity toEntityByRequest(SerologyTestRequest request);
}
