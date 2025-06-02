package com.yawarSoft.Modules.Laboratory.Mappers;

import com.yawarSoft.Core.Entities.HematologicalTestEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Laboratory.Dto.HematologicalTestDTO;
import com.yawarSoft.Modules.Laboratory.Dto.Request.HematologicalTestRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HematologicalMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    HematologicalTestDTO toDto(HematologicalTestEntity entity);

    HematologicalTestEntity toEntityByRequest(HematologicalTestRequest request);
}
