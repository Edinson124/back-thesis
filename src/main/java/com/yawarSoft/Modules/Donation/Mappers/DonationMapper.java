package com.yawarSoft.Modules.Donation.Mappers;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.DonationEntity;
import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.MapperUtils;
import com.yawarSoft.Modules.Donation.Dto.DonationRelationsDTO;
import com.yawarSoft.Modules.Donation.Dto.DonationUpdateDTO;
import com.yawarSoft.Modules.Donation.Dto.DonationViewDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.DonationResponseDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationGetDTO;
import org.mapstruct.*;

import java.util.Base64;

@Mapper(componentModel = "spring",uses = MapperUtils.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DonationMapper {

    @Mapping(source = "bloodBank.id", target = "bloodBankId")
    @Mapping(target = "donorDocumentType", expression = "java(decryptFromBytes(entity.getDonor().getDocumentType(), aesUtil))")
    @Mapping(target = "donorDocumentNumber", expression = "java(decryptFromBytes(entity.getDonor().getDocumentNumber(), aesUtil))")
    @Mapping(source = "donor", target = "donorFullName", qualifiedByName = "getFullNamePerson")
    @Mapping(target = "patientDocumentType", expression = "java(entity.getPatient() != null ? decryptFromBytes(entity.getPatient().getDocumentType(), aesUtil) : null)")
    @Mapping(target = "patientDocumentNumber", expression = "java(entity.getPatient() != null ? decryptFromBytes(entity.getPatient().getDocumentNumber(), aesUtil) : null)")
    @Mapping(source = "patient", target = "patientFullName", qualifiedByName = "getFullNamePerson")
    @Mapping(source = "date", target = "date")
    DonationResponseDTO toResponseDto(DonationEntity entity,@Context AESGCMEncryptionUtil aesUtil);

    @Mapping(source = "bloodBank.id", target = "bloodBankId")
    @Mapping(target = "donorDocumentType", expression = "java(decryptFromBytes(entity.getDonor().getDocumentType(), aesUtil))")
    @Mapping(target = "donorDocumentNumber", expression = "java(decryptFromBytes(entity.getDonor().getDocumentNumber(), aesUtil))")
    @Mapping(source = "donor", target = "donorFullName", qualifiedByName = "getFullNamePerson")
    @Mapping(target = "patientDocumentType", expression = "java(entity.getPatient() != null ? decryptFromBytes(entity.getPatient().getDocumentType(), aesUtil) : null)")
    @Mapping(target = "patientDocumentNumber", expression = "java(entity.getPatient() != null ? decryptFromBytes(entity.getPatient().getDocumentNumber(), aesUtil) : null)")
    @Mapping(source = "patient", target = "patientFullName", qualifiedByName = "getFullNamePerson")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy", target = "createdByName", qualifiedByName = "getFullName")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    @Mapping(source = "updatedBy", target = "updatedByName", qualifiedByName = "getFullName")

    @Mapping(source = "physicalAssessment.id", target = "physicalAssessmentId")
    @Mapping(source = "interviewAnswer.id", target = "interviewAnswerId")
    @Mapping(source = "bloodExtraction.id", target = "bloodExtractionId")

    @Mapping(source = "physicalAssessment.createdBy.id", target = "createdByIdPhysicalAssessment")
    @Mapping(source = "physicalAssessment.createdBy", target = "createdByNamePhysicalAssessment", qualifiedByName = "getFullName")
    @Mapping(source = "physicalAssessment.createdAt", target = "createdAtPhysicalAssessment")
    @Mapping(source = "physicalAssessment.updatedBy.id", target = "updatedByIdPhysicalAssessment")
    @Mapping(source = "physicalAssessment.updatedBy", target = "updatedByNamePhysicalAssessment", qualifiedByName = "getFullName")
    @Mapping(source = "physicalAssessment.updatedAt", target = "updatedAtPhysicalAssessment")

    @Mapping(source = "interviewAnswer.createdBy.id", target = "createdByIdInterviewAnswer")
    @Mapping(source = "interviewAnswer.createdBy", target = "createdByNameInterviewAnswer", qualifiedByName = "getFullName")
    @Mapping(source = "interviewAnswer.createdAt", target = "createdAtInterviewAnswer")

    @Mapping(source = "bloodExtraction.createdBy.id", target = "createdByIdBloodExtraction")
    @Mapping(source = "bloodExtraction.createdBy", target = "createdByNameBloodExtraction", qualifiedByName = "getFullName")
    @Mapping(source = "bloodExtraction.createdAt", target = "createdAtBloodExtraction")
    @Mapping(source = "bloodExtraction.updatedBy.id", target = "updatedByIdBloodExtraction")
    @Mapping(source = "bloodExtraction.updatedBy", target = "updatedByNameBloodExtraction", qualifiedByName = "getFullName")
    @Mapping(source = "bloodExtraction.updatedAt", target = "updatedAtBloodExtraction")
    DonationViewDTO toDonationViewDTO(DonationEntity entity,@Context AESGCMEncryptionUtil aesUtil);


    @Mapping(target = "patient.id", source = "patientId")
    void updateEntityFromDto(DonationUpdateDTO request, @MappingTarget DonationEntity entity);

    @Mapping(source = "bloodBank.name", target = "bloodBankName")
    @Mapping(source = "date", target = "date")
    DonationByDonorDTO toDonationByDonorDTO(DonationEntity donationEntity);

    @Mapping(source = "bloodBank.id", target = "idBloodBank")
    @Mapping(source = "physicalAssessment.id", target = "idPhysicalAssessment")
    @Mapping(source = "interviewAnswer.id", target = "idInterviewAnswer")
    @Mapping(source = "hematologicalTest.id", target = "idHematologicalTest")
    @Mapping(source = "serologyTest.id", target = "idSerologyTest")
    @Mapping(source = "bloodExtraction.id", target = "idBloodExtraction")
    DonationRelationsDTO toDonationRelationsDTO(DonationEntity donationEntity);

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
