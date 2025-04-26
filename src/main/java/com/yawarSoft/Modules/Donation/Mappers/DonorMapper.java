package com.yawarSoft.Modules.Donation.Mappers;

import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Modules.Donation.Dto.DonorDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DonorMapper {

    @Mappings({
            @Mapping(target = "firstName", expression = "java(encryptToBytes(donorDTO.getFirstName(), aesUtil))"),
            @Mapping(target = "lastName", expression = "java(encryptToBytes(donorDTO.getLastName(), aesUtil))"),
            @Mapping(target = "secondLastName", expression = "java(encryptToBytes(donorDTO.getSecondLastName(), aesUtil))"),
            @Mapping(target = "documentType", expression = "java(encryptToBytes(donorDTO.getDocumentType(), aesUtil))"),
            @Mapping(target = "documentNumber", expression = "java(encryptToBytes(donorDTO.getDocumentNumber(), aesUtil))"),
            @Mapping(target = "address", expression = "java(encryptToBytes(donorDTO.getAddress(), aesUtil))"),
            @Mapping(target = "phone", expression = "java(encryptToBytes(donorDTO.getPhone(), aesUtil))"),
            @Mapping(target = "email", expression = "java(encryptToBytes(donorDTO.getEmail(), aesUtil))")
    })
    DonorEntity toEntity(DonorDTO donorDTO, @Context AESGCMEncryptionUtil aesUtil);

    @Mappings({
            @Mapping(target = "firstName", expression = "java(decryptFromBytes(donorEntity.getFirstName(), aesUtil))"),
            @Mapping(target = "lastName", expression = "java(decryptFromBytes(donorEntity.getLastName(), aesUtil))"),
            @Mapping(target = "secondLastName", expression = "java(decryptFromBytes(donorEntity.getSecondLastName(), aesUtil))"),
            @Mapping(target = "documentType", expression = "java(decryptFromBytes(donorEntity.getDocumentType(), aesUtil))"),
            @Mapping(target = "documentNumber", expression = "java(decryptFromBytes(donorEntity.getDocumentNumber(), aesUtil))"),
            @Mapping(target = "address", expression = "java(decryptFromBytes(donorEntity.getAddress(), aesUtil))"),
            @Mapping(target = "phone", expression = "java(decryptFromBytes(donorEntity.getPhone(), aesUtil))"),
            @Mapping(target = "email", expression = "java(decryptFromBytes(donorEntity.getEmail(), aesUtil))"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "deferralEndDate", source = "deferralEndDate"),
            // mapea también el resto normalmente
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
