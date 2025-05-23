package com.yawarSoft.Modules.Admin.Mappers;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Admin.Dto.BloodBankDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankListDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Dto.Reponse.BloodBankOptionsAddNetworkDTO;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
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

    @Mapping(source = "bloodBankType", target = "bloodBankType")
    List<BloodBankSelectOptionDTO> toSelectDtoListFromProjectionList(List<BloodBankProjectionSelect> projections);

    BloodBankOptionsAddNetworkDTO toOptionNetworkDTO(BloodBankEntity bloodBankEntity);
}
