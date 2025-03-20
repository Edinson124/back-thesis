package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Dto.UserListDTO;
import com.yawarSoft.Entities.BloodBankEntity;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", source = "roles", qualifiedByName = "getFirstRole")
    @Mapping(target = "bloodBankId", source = "bloodBank.id")
    UserListDTO toListDto(UserEntity userEntity);

    @Mapping(target = "bloodBank", source = "bloodBankId", qualifiedByName = "mapBloodBank")
    UserEntity toEntityByListDTO(UserListDTO userListDTO);

    @Named("getFirstRole")
    default String getFirstRole(Set<RoleEntity> roles) {
        return roles.stream().findFirst().map(RoleEntity::getName).orElse(null);
    }

    @Mapping(target = "roles", source = "roles", qualifiedByName = "getSetIdRoles")
    @Mapping(target = "bloodBankId", source = "bloodBank.id")
    UserDTO toUserDto(UserEntity userEntity);

    @Mapping(target = "bloodBank", source = "bloodBankId", qualifiedByName = "mapBloodBank")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserEntity toEntityByUserDTO(UserDTO userDTO);

    @Named("getSetIdRoles")
    default Set<Integer> getSetIdRoles(Set<RoleEntity> roles) {
        return roles.stream().map(RoleEntity::getId).collect(Collectors.toSet());
    }

    @Named("mapRoles")
    default Set<RoleEntity> mapRoles(Set<Integer> roleIds) {
        if (roleIds == null) {
            return new HashSet<>();
        }
        return roleIds.stream()
                .map(id -> {
                    RoleEntity role = new RoleEntity();
                    role.setId(id);
                    return role;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapBloodBank")
    default BloodBankEntity mapBloodBank(Integer bloodBankId) {
        if (bloodBankId == null) return null;
        BloodBankEntity bloodBank = new BloodBankEntity();
        bloodBank.setId(bloodBankId);
        return bloodBank;
    }

}
