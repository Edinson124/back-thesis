package com.yawarSoft.Modules.Donation.Mappers;

import com.yawarSoft.Core.Entities.BloodExtractionEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Donation.Dto.Request.BloodExtractionRequest;
import com.yawarSoft.Modules.Donation.Dto.BloodExtractionDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BloodExtractionMapper {

    // Para mapear de Request a Entity
    BloodExtractionEntity toEntity(BloodExtractionRequest request);

    // Para mapear de Entity a DTO
    @Mapping(source = "arm", target = "arm")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    BloodExtractionDTO toDto(BloodExtractionEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BloodExtractionRequest dto, @MappingTarget BloodExtractionEntity entity);

}
