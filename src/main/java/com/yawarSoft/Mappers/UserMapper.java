package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Dto.UserListDTO;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "bloodBankId", source = "bloodBank.id")
    UserListDTO toListDto(UserEntity userEntity);

    @Mapping(target = "bloodBank", source = "bloodBankId", qualifiedByName = "mapBloodBank")
    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    UserEntity toEntityByListDTO(UserListDTO userListDTO);

    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "bloodBankId", source = "bloodBank.id")
    UserDTO toUserDto(UserEntity userEntity);

    @Mapping(target = "bloodBank", source = "bloodBankId", qualifiedByName = "mapBloodBank")
    @Mapping(target = "role", source = "roleId", qualifiedByName = "mapRole")
    UserEntity toEntityByUserDTO(UserDTO userDTO);

    @Named("mapRole")
    default RoleEntity mapRole(Integer roleId) {
        if (roleId == null) return null;
        RoleEntity role = new RoleEntity();
        role.setId(roleId);
        return role;
    }

    @Named("mapBloodBank")
    default BloodBankEntity mapBloodBank(Integer bloodBankId) {
        if (bloodBankId == null) return null;
        BloodBankEntity bloodBank = new BloodBankEntity();
        bloodBank.setId(bloodBankId);
        return bloodBank;
    }

}
