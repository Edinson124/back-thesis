package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.BloodBankDTO;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BloodBankMapper {

    @Mapping(source = "bloodBankType.name", target = "type")  // Mapea el campo `type.name` a `type` en el DTO
    BloodBankDTO toDTO(BloodBankEntity bloodBankEntity);
}
