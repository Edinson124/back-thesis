package com.yawarSoft.Mappers;

import com.yawarSoft.Dto.UserDTO;
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
    UserDTO toDto(UserEntity userEntity);

    @Named("getFirstRole")
    default String getFirstRole(Set<RoleEntity> roles) {
        return roles.stream().findFirst().map(RoleEntity::getName).orElse(null);
    }
}
