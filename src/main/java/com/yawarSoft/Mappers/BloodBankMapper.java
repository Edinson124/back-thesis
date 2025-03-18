package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.BloodBankDTO;
import com.yawarSoft.Entities.BloodBankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BloodBankMapper {

    BloodBankDTO toDTO(BloodBankEntity bloodBankEntity);
}
