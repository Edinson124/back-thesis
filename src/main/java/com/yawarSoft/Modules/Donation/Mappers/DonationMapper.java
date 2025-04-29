package com.yawarSoft.Modules.Donation.Mappers;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.DonationEntity;
import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Modules.Donation.Dto.DonationUpdateDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.DonationResponseDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DonationMapper {

    @Mapping(target = "bloodBank", source = "bloodBankId")
    @Mapping(target = "donor", source = "donorId")
    @Mapping(target = "patient", source = "patientId")
    DonationEntity toEntityByDonationCreateRequest(DonationCreateRequest request);

    default BloodBankEntity mapBloodBankId(Integer id) {
        if (id == null) return null;
        BloodBankEntity entity = new BloodBankEntity();
        entity.setId(id);
        return entity;
    }

    default DonorEntity mapDonorId(Long id) {
        if (id == null) return null;
        DonorEntity entity = new DonorEntity();
        entity.setId(id);
        return entity;
    }

    default PatientEntity mapPatientId(Long id) {
        if (id == null) return null;
        PatientEntity entity = new PatientEntity();
        entity.setId(id);
        return entity;
    }

    @Mapping(source = "bloodBank.id", target = "bloodBankId")
    @Mapping(source = "donor.id", target = "donorId")
    @Mapping(source = "patient.id", target = "patientId")
    DonationResponseDTO toResponseDto(DonationEntity entity);

    @Mapping(target = "patient.id", source = "patientId")
    void updateEntityFromDto(DonationUpdateDTO request, @MappingTarget DonationEntity entity);

    DonationByDonorDTO toDonationByDonorDTO(DonationEntity donationEntity);
}
