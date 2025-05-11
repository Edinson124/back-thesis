package com.yawarSoft.Modules.Transfusion.Mappers;

import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionDetailDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TranfusionListDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionByPatientDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionViewDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransfusionRequestMapper {

    @Mapping(source = "bloodBank.name", target = "bloodBankName")
    @Mapping(source = "attendingDoctor", target = "attendingDoctorName", qualifiedByName = "getFullName")
    @Mapping(source = "date", target = "date")
    TransfusionByPatientDTO toTransfusionByPatientDto(TransfusionRequestEntity donationEntity);

    @Mapping(target = "patientDocumentNumber", expression = "java(decryptFromBytes(entity.getPatient().getDocumentNumber(), aesUtil))")
    @Mapping(source = "patient", target = "patientName", qualifiedByName = "getFullNamePerson")
    @Mapping(source = "attendingDoctor", target = "attendingDoctorName", qualifiedByName = "getFullName")
    @Mapping(source = "date", target = "date")
    TranfusionListDTO toListDTO(TransfusionRequestEntity entity,@Context AESGCMEncryptionUtil aesUtil);

    @Mapping(target = "patientDocumentNumber", expression = "java(decryptFromBytes(entity.getPatient().getDocumentNumber(), aesUtil))")
    @Mapping(source = "patient", target = "patientName", qualifiedByName = "getFullNamePerson")
    @Mapping(source = "patient.bloodType", target = "patientBloodType")
    @Mapping(source = "patient.rhFactor", target = "patientRhFactor")
    @Mapping(source = "attendingDoctor", target = "attendingDoctorName", qualifiedByName = "getFullName")
    @Mapping(source = "transfusionResult.id", target = "transfusionResultId")
    TransfusionDetailDTO toDetailDTO(TransfusionRequestEntity entity, @Context AESGCMEncryptionUtil aesUtil);

    @Mapping(target = "patientDocumentType", expression = "java(decryptFromBytes(entity.getPatient().getDocumentType(), aesUtil))")
    @Mapping(target = "patientDocumentNumber", expression = "java(decryptFromBytes(entity.getPatient().getDocumentNumber(), aesUtil))")
    @Mapping(source = "patient", target = "patientName", qualifiedByName = "getFullNamePerson")
    @Mapping(source = "patient.bloodType", target = "patientBloodType")
    @Mapping(source = "patient.rhFactor", target = "patientRhFactor")
    @Mapping(source = "attendingDoctor", target = "attendingDoctorName", qualifiedByName = "getFullName")
    @Mapping(source = "date", target = "date")
    TransfusionViewDTO toTransfusionViewTO(TransfusionRequestEntity entity, @Context AESGCMEncryptionUtil aesUtil);

    default String decryptFromBytes(byte[] value, AESGCMEncryptionUtil aesUtil) {
        if (value == null) return null;
        try {
            String encrypted = new String(value);
            return aesUtil.decrypt(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting field: " + e.getMessage(), e);
        }
    }
}
