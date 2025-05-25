package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Admin.Dto.BloodBankNetworkDetailsDTO;
import com.yawarSoft.Modules.Admin.Dto.NetworkDTO;
import com.yawarSoft.Core.Entities.BloodBankNetworkEntity;
import com.yawarSoft.Core.Entities.NetworkEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BBNetworkMapper {

    @Mapping(target = "bloodBankDetails", source = "bloodBankRelations")
    NetworkDTO toDto(NetworkEntity networkEntity);

    @Mapping(target = "id", source = "bloodBank.id")
    @Mapping(target = "name", source = "bloodBank.name")
    @Mapping(target = "coordinatorName", source = "bloodBank.coordinator", qualifiedByName = "mapFullName")
    @Mapping(target = "createdByFullName", source = "createdBy", qualifiedByName = "mapFullName")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "disassociatedAt", source = "disassociatedAt")
    @Mapping(target = "disassociatedByFullName", source = "disassociatedBy", qualifiedByName = "mapFullName")
    @Mapping(target = "status", source = "status")
    BloodBankNetworkDetailsDTO toDetailsDto(BloodBankNetworkEntity relationEntity);

    @Named("mapFullName")
    default String mapFullName(UserEntity user) {
        if (user == null) return null;
        return user.getFirstName() + " " + user.getLastName() + " " + user.getSecondLastName();
    }
}
