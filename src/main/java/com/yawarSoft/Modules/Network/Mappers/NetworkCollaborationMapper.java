package com.yawarSoft.Modules.Network.Mappers;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.BloodBankNetworkEntity;
import com.yawarSoft.Core.Entities.NetworkEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NetworkCollaborationMapper {

    @Mapping(target = "bloodBankDetails", source = "bloodBankRelations")
    NetworkCollaborationDTO toDto(NetworkEntity networkEntity);

    @Mapping(target = "id", source = "bloodBank.id")
    @Mapping(target = "name", source = "bloodBank.name")
    @Mapping(target = "coordinatorName", source = "bloodBank.coordinator", qualifiedByName = "getFullName")
    @Mapping(target = "address", source = "bloodBank.address")
    @Mapping(target = "ubication", source = "bloodBank", qualifiedByName = "getUbication")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "type", source = "bloodBank.bloodBankType.name")
    @Mapping(target = "isInternal", source = "bloodBank.isInternal")
    BloodBankNetworkCollaborationDTO toDetailsDto(BloodBankNetworkEntity relationEntity);

    @Mapping(target = "coordinatorName", source = "coordinator", qualifiedByName = "getFullName")
    @Mapping(target = "ubication", source = ".", qualifiedByName = "getUbication")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "type", source = "bloodBankType.name")
    BloodBankNetworkCollaborationDTO toDetailsDtoByBloodBank(BloodBankEntity relationEntity);

}
