package com.yawarSoft.Modules.Network.Mappers;

import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Network.Dto.UnitCollaborationTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitCollaborationMapper {
    @Mapping(source = "donation.id", target = "donationId")
    UnitCollaborationTableDTO toTableDTO(UnitEntity unitEntity);
}
