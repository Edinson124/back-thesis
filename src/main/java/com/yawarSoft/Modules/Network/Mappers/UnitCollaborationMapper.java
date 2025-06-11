package com.yawarSoft.Modules.Network.Mappers;

import com.yawarSoft.Core.Entities.SerologyTestEntity;
import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Network.Dto.SerologyTestNetworkDTO;
import com.yawarSoft.Modules.Network.Dto.UnitCollaborationTableDTO;
import com.yawarSoft.Modules.Network.Dto.UnitNetworkDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitCollaborationMapper {
    @Mapping(source = "donation.id", target = "donationId")
    @Mapping(source = "stampPronahebas", target = "stampPronahebas")
    UnitCollaborationTableDTO toTableDTO(UnitEntity unitEntity);

    @Mapping(source = "donation.id", target = "donationId")
    UnitNetworkDTO toUnitDTO(UnitEntity unitEntity);

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    SerologyTestNetworkDTO toSerologyDto(SerologyTestEntity entity);
}
