package com.yawarSoft.Modules.Donation.Mappers;

import com.yawarSoft.Core.Entities.PhysicalAssessmentEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Donation.Dto.PhysicalAssessmentDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.PhysicalAssessmentRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhysicalAssesmentMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    PhysicalAssessmentDTO toDto(PhysicalAssessmentEntity entity);

    @Mapping(target = "createdBy.id", source = "createdById")
    @Mapping(target = "updatedBy.id", source = "updatedById")
    PhysicalAssessmentEntity toEntity(PhysicalAssessmentDTO dto);

    PhysicalAssessmentEntity toEntityByRequest(PhysicalAssessmentRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PhysicalAssessmentRequest dto, @MappingTarget PhysicalAssessmentEntity entity);

}
