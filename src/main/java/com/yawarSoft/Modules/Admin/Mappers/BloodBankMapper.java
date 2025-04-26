package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Admin.Dto.BloodBankListDTO;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankDTO;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BloodBankMapper {

    // Mapeo de BloodBankRequest a BloodBankEntity
    @Mapping(source = "idCoordinator", target = "coordinator.id") // Mapea el id del coordinador
    @Mapping(source = "idType", target = "bloodBankType.id") // Mapea el id del tipo de banco de sangre
    BloodBankEntity toEntity(BloodBankDTO bloodBankDTO);

    @Mapping(source = "bloodBankType.name", target = "type")
    @Mapping(source = "coordinator.id", target = "idCoordinator")
    @Mapping(target = "fullNameCoordinator", source = "coordinator", qualifiedByName = "getFullName")
    BloodBankListDTO toListDTO(BloodBankEntity bloodBankEntity);

    @Mapping(source = "bloodBankType.id", target = "idType")
    @Mapping(source = "coordinator.id", target = "idCoordinator")
    BloodBankDTO toDTO(BloodBankEntity bloodBankEntity);

    @Named("getFullName")
    public static String getFullName(UserEntity coordinator) {
        if (coordinator == null) return null;
        return String.join(" ",
                Optional.ofNullable(coordinator.getFirstName()).orElse(""),
                Optional.ofNullable(coordinator.getLastName()).orElse(""),
                Optional.ofNullable(coordinator.getSecondLastName()).orElse("")
        ).trim();
    }

    @Mapping(source = "bloodBankType", target = "bloodBankType")
    List<BloodBankSelectOptionDTO> toSelectDtoListFromProjectionList(List<BloodBankProjectionSelect> projections);
}
