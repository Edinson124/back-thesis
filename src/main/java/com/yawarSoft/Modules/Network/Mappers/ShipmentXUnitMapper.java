package com.yawarSoft.Modules.Network.Mappers;

import com.yawarSoft.Core.Entities.ShipmentXUnitEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Network.Dto.ShipmentXUnitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentXUnitMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idUnit", source = "bloodUnit.id")
    @Mapping(target = "bloodType", source = "bloodUnit.bloodType")
    @Mapping(target = "unitType", source = "bloodUnit.unitType")
    @Mapping(target = "expirationDate", source = "bloodUnit.expirationDate")
    ShipmentXUnitDTO toDto(ShipmentXUnitEntity entity);

    List<ShipmentXUnitDTO> toDto(List<ShipmentXUnitEntity> entity);
}
