package com.yawarSoft.Modules.Transfusion.Mappers;

import com.yawarSoft.Core.Entities.TransfusionRequestDetailEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionRequestDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransfusionRequestDetailMapper {

    TransfusionRequestDetailDTO toTransfusionRequestDetailDto(TransfusionRequestDetailEntity entity);

    List<TransfusionRequestDetailDTO> toTransfusionRequestDetailDtoList(List<TransfusionRequestDetailEntity> entityList);

    @Mapping(target = "id", ignore = true)
    TransfusionRequestDetailEntity toEntityByRequestCreate(TransfusionRequestDetailDTO detailDTO);
    List<TransfusionRequestDetailEntity> toEntityByRequestCreate(List<TransfusionRequestDetailDTO> entityList);
}
