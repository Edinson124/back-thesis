package com.yawarSoft.Modules.Storage.Mappers;

import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitExtractionDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Dto.UnitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper {

    @Mapping(source = "donation.id", target = "donationId")
    @Mapping(source = "stampPronahebas", target = "stampPronahebas")
    UnitListDTO toListDTO(UnitEntity unitEntity);

    @Mapping(source = "donation.id", target = "donationId")
    UnitDTO toDTO(UnitEntity unitEntity);

    @Mapping(source = "unitType", target = "type")
    @Mapping(source = "bagType", target = "bag")
    @Mapping(source = "stampPronahebas", target = "stampPronahebas")
    UnitExtractionDTO toExtractionDTO(UnitEntity unitEntity);

    @Mapping(source = "type", target = "unitType")
    @Mapping(source = "bag", target = "bagType")
    @Mapping(target = "fromDonation", constant = "true")
    UnitEntity toEntityByExtractionDTO(UnitExtractionDTO unitExtractionDTO);
}
