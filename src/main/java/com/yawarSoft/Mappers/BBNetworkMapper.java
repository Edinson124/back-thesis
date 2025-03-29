package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.BloodBankNetworkDetailsDTO;
import com.yawarSoft.Dto.NetworkDTO;
import com.yawarSoft.Entities.BloodBankNetworkEntity;
import com.yawarSoft.Entities.NetworkEntity;
import com.yawarSoft.Entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
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
