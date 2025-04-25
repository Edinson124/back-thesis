package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Modules.Admin.Dto.BloodBankDTO;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BloodBankMapper {

    @Mapping(source = "bloodBankType.name", target = "type")  // Mapea el campo `type.name` a `type` en el DTO
    BloodBankDTO toDTO(BloodBankEntity bloodBankEntity);

    List<BloodBankSelectOptionDTO> toSelectDtoListFromProjectionList(List<BloodBankProjectionSelect> projections);
}
