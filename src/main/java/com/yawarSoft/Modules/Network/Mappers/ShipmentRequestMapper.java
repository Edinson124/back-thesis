package com.yawarSoft.Modules.Network.Mappers;

import com.yawarSoft.Core.Entities.ShipmentRequestDetailEntity;
import com.yawarSoft.Core.Entities.ShipmentRequestEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Network.Dto.Request.RequestDetailsUnitDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestDataDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentRequestMapper {


    @Mapping(target = "bloodBankNameOrigin", source = "originBank.name")
    @Mapping(target = "bloodBankNameDestination", source = "destinationBank.name")
    @Mapping(target = "createdByName", source = "createdBy", qualifiedByName = "getFullName")
    @Mapping(target = "status", source = "status")
    ShipmentRequestTableDTO toDto(ShipmentRequestEntity networkEntity);

    ShipmentRequestDataDTO toDtoData(ShipmentRequestEntity networkEntity);

    @Mapping(target = "bloodGroup", source = "bloodType")
    RequestDetailsUnitDTO toDetailDTO(ShipmentRequestDetailEntity networkEntity);

    @Mapping(target = "bloodGroup", source = "bloodType")
    List<RequestDetailsUnitDTO> toDetailDTO(List<ShipmentRequestDetailEntity> entityList);
}
