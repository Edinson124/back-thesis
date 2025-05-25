package com.yawarSoft.Modules.Network.Mappers;

import com.yawarSoft.Core.Entities.ShipmentRequestEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentRequestMapper {


    ShipmentRequestTableDTO toDto(ShipmentRequestEntity networkEntity);
}
