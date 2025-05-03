package com.yawarSoft.Modules.Donation.Mappers;

import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DonorMapper {

    @Mappings({
            @Mapping(target = "firstName", expression = "java(encryptToBytes(donorRequestDTO.getFirstName(), aesUtil))"),
            @Mapping(target = "lastName", expression = "java(encryptToBytes(donorRequestDTO.getLastName(), aesUtil))"),
            @Mapping(target = "secondLastName", expression = "java(encryptToBytes(donorRequestDTO.getSecondLastName(), aesUtil))"),
            @Mapping(target = "documentType", expression = "java(encryptToBytes(donorRequestDTO.getDocumentType(), aesUtil))"),
            @Mapping(target = "documentNumber", expression = "java(encryptToBytes(donorRequestDTO.getDocumentNumber(), aesUtil))"),
            @Mapping(target = "address", expression = "java(encryptToBytes(donorRequestDTO.getAddress(), aesUtil))"),
            @Mapping(target = "phone", expression = "java(encryptToBytes(donorRequestDTO.getPhone(), aesUtil))"),
            @Mapping(target = "email", expression = "java(encryptToBytes(donorRequestDTO.getEmail(), aesUtil))")
    })
    DonorEntity toEntity(DonorRequestDTO donorRequestDTO, @Context AESGCMEncryptionUtil aesUtil);

    @Mappings({
            @Mapping(target = "firstName", expression = "java(decryptFromBytes(donorEntity.getFirstName(), aesUtil))"),
            @Mapping(target = "lastName", expression = "java(decryptFromBytes(donorEntity.getLastName(), aesUtil))"),
            @Mapping(target = "secondLastName", expression = "java(decryptFromBytes(donorEntity.getSecondLastName(), aesUtil))"),
            @Mapping(target = "documentType", expression = "java(decryptFromBytes(donorEntity.getDocumentType(), aesUtil))"),
            @Mapping(target = "documentNumber", expression = "java(decryptFromBytes(donorEntity.getDocumentNumber(), aesUtil))"),
            @Mapping(target = "address", expression = "java(decryptFromBytes(donorEntity.getAddress(), aesUtil))"),
            @Mapping(target = "phone", expression = "java(decryptFromBytes(donorEntity.getPhone(), aesUtil))"),
            @Mapping(target = "email", expression = "java(decryptFromBytes(donorEntity.getEmail(), aesUtil))"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName"),
            @Mapping(source = "updatedBy.id", target = "updatedById"),
            @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName"),
            @Mapping(source = "occupation", target = "occupation")
    })
    DonorGetDTO toGetDto(DonorEntity donorEntity, @Context AESGCMEncryptionUtil aesUtil);

    default byte[] encryptToBytes(String value, AESGCMEncryptionUtil aesUtil) {
        if (value == null) return null;
        try {
            String encrypted = aesUtil.encrypt(value);
            return encrypted.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting field: " + e.getMessage(), e);
        }
    }

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
