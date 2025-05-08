package com.yawarSoft.Modules.Storage.Mappers;

import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Dto.UnitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper {

    @Mapping(source = "donation.id", target = "donationId")
    UnitListDTO toListDTO(UnitEntity unitEntity);

    @Mapping(source = "donation.id", target = "donationId")
    UnitDTO toDTO(UnitEntity unitEntity);
}
