package com.yawarSoft.Modules.Interoperability.Mappers;

import com.yawarSoft.Core.Entities.AuthExternalSystemEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Interoperability.Dtos.AuthExternalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthExternalSystemMapper {

    @Mapping(source = "bloodBank.id", target = "bloodBankId")
    @Mapping(source = "bloodBank.name", target = "bloodBankName")
    AuthExternalDTO toDTO(AuthExternalSystemEntity entity);
}
