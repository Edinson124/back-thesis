package com.yawarSoft.Modules.Donation.Mappers;

import com.yawarSoft.Core.Entities.InterviewAnswerEntity;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Donation.Dto.InterviewAnswerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterviewAnswerMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    InterviewAnswerDTO toDto(InterviewAnswerEntity entity);
}
