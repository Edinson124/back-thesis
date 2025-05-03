package com.yawarSoft.Modules.Transfusion.Mappers;

import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Transfusion.Dto.PatientGetDTO;
import org.mapstruct.*;

import java.util.Base64;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

    @Mappings({
            @Mapping(target = "firstName", expression = "java(decryptFromBytes(patientEntity.getFirstName(), aesUtil))"),
            @Mapping(target = "lastName", expression = "java(decryptFromBytes(patientEntity.getLastName(), aesUtil))"),
            @Mapping(target = "secondLastName", expression = "java(decryptFromBytes(patientEntity.getSecondLastName(), aesUtil))"),
            @Mapping(target = "documentType", expression = "java(decryptFromBytes(patientEntity.getDocumentType(), aesUtil))"),
            @Mapping(target = "documentNumber", expression = "java(decryptFromBytes(patientEntity.getDocumentNumber(), aesUtil))"),
            @Mapping(target = "address", expression = "java(decryptFromBytes(patientEntity.getAddress(), aesUtil))"),
            @Mapping(target = "phone", expression = "java(decryptFromBytes(patientEntity.getPhone(), aesUtil))"),
            @Mapping(target = "email", expression = "java(decryptFromBytes(patientEntity.getEmail(), aesUtil))"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName"),
            @Mapping(source = "updatedBy.id", target = "updatedById"),
            @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")
    })
    PatientGetDTO toGetDto(PatientEntity patientEntity, @Context AESGCMEncryptionUtil aesUtil);

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
