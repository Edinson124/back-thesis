package com.yawarSoft.Modules.Transfusion.Mappers;

import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Transfusion.Dto.PatientGetDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Request.PatientRequestDTO;
import org.mapstruct.*;

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

    @Mappings({
            @Mapping(target = "firstName", expression = "java(encryptToBytes(patientRequestDTO.getFirstName(), aesUtil))"),
            @Mapping(target = "lastName", expression = "java(encryptToBytes(patientRequestDTO.getLastName(), aesUtil))"),
            @Mapping(target = "secondLastName", expression = "java(encryptToBytes(patientRequestDTO.getSecondLastName(), aesUtil))"),
            @Mapping(target = "documentType", expression = "java(encryptToBytes(patientRequestDTO.getDocumentType(), aesUtil))"),
            @Mapping(target = "documentNumber", expression = "java(encryptToBytes(patientRequestDTO.getDocumentNumber(), aesUtil))"),
            @Mapping(target = "address", expression = "java(encryptToBytes(patientRequestDTO.getAddress(), aesUtil))"),
            @Mapping(target = "phone", expression = "java(encryptToBytes(patientRequestDTO.getPhone(), aesUtil))"),
            @Mapping(target = "email", expression = "java(encryptToBytes(patientRequestDTO.getEmail(), aesUtil))")
    })
    PatientEntity toEntity(PatientRequestDTO patientRequestDTO, @Context AESGCMEncryptionUtil aesUtil);

    @Mappings({
            @Mapping(target = "firstName", expression = "java(encryptToBytes(request.getFirstName(), aesUtil))"),
            @Mapping(target = "lastName", expression = "java(encryptToBytes(request.getLastName(), aesUtil))"),
            @Mapping(target = "secondLastName", expression = "java(encryptToBytes(request.getSecondLastName(), aesUtil))"),
            @Mapping(target = "documentType", expression = "java(encryptToBytes(request.getDocumentType(), aesUtil))"),
            @Mapping(target = "documentNumber", expression = "java(encryptToBytes(request.getDocumentNumber(), aesUtil))"),
            @Mapping(target = "address", expression = "java(encryptToBytes(request.getAddress(), aesUtil))"),
            @Mapping(target = "phone", expression = "java(encryptToBytes(request.getPhone(), aesUtil))"),
            @Mapping(target = "email", expression = "java(encryptToBytes(request.getEmail(), aesUtil))")
    })
    void updateEntityFromDto(PatientRequestDTO request, @MappingTarget PatientEntity entity, @Context AESGCMEncryptionUtil aesUtil);


    default String decryptFromBytes(byte[] value, AESGCMEncryptionUtil aesUtil) {
        if (value == null) return null;
        try {
            String encrypted = new String(value);
            return aesUtil.decrypt(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting field: " + e.getMessage(), e);
        }
    }
    default byte[] encryptToBytes(String value, AESGCMEncryptionUtil aesUtil) {
        if (value == null) return null;
        try {
            String encrypted = aesUtil.encrypt(value);
            return encrypted.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting field: " + e.getMessage(), e);
        }
    }
}
