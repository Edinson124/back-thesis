package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Entities.BloodBankEntity;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", source = "roles", qualifiedByName = "getFirstRole")
    @Mapping(target = "bloodBankId", source = "bloodBank.id")
    UserDTO toDto(UserEntity userEntity);

    @Mapping(target = "bloodBank", source = "bloodBankId", qualifiedByName = "mapBloodBank")
    UserEntity toEntity(UserDTO userDTO);

    @Named("getFirstRole")
    default String getFirstRole(Set<RoleEntity> roles) {
        return roles.stream().findFirst().map(RoleEntity::getName).orElse(null);
    }

    @Named("mapBloodBank")
    default BloodBankEntity mapBloodBank(Long bloodBankId) {
        if (bloodBankId == null) return null;
        BloodBankEntity bloodBank = new BloodBankEntity();
        bloodBank.setId(bloodBankId);
        return bloodBank;
    }

}
