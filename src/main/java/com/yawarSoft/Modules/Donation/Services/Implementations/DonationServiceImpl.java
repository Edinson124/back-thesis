package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.DonationEntity;
import com.yawarSoft.Core.Entities.DonorEntity;
import com.yawarSoft.Core.Entities.PatientEntity;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.DonationUpdateDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.DonationResponseDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Donation.Enums.DonationStatus;
import com.yawarSoft.Modules.Donation.Mappers.DonationMapper;
import com.yawarSoft.Modules.Donation.Repositories.DonationRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonorService;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final PatientService patientService;
    private final DonorService donorService;


    public DonationServiceImpl(DonationRepository donationRepository, DonationMapper donationMapper, PatientService patientService, DonorService donorService) {
        this.donationRepository = donationRepository;
        this.donationMapper = donationMapper;
        this.patientService = patientService;
        this.donorService = donorService;
    }

    @Override
    @Transactional
    public Long createDonation(DonationCreateRequest donationCreateRequest) {

        Long donorId = donorService.getIdDonor(donationCreateRequest.getDocumentTypeDonor(),donationCreateRequest.getDocumentNumberDonor());
        if (donorId == 0) {
            throw new IllegalArgumentException("Donante no encontrado con el documento tipo: " + donationCreateRequest.getDocumentTypeDonor() + " - número: " + donationCreateRequest.getDocumentNumberDonor());
        }
        DonorEntity donor = DonorEntity.builder().id(donorId).build();
        PatientEntity patient = null;

        if (donationCreateRequest.getDocumentTypePatient() != null && !donationCreateRequest.getDocumentTypePatient().isBlank()
                && donationCreateRequest.getDocumentNumberPatient() != null && !donationCreateRequest.getDocumentNumberPatient().isBlank()) {

            Long patientId = patientService.getIdPatient(donationCreateRequest.getDocumentTypePatient(),donationCreateRequest.getDocumentNumberPatient());

            if (patientId == 0) {
                throw new IllegalArgumentException("Paciente no encontrado con el documento tipo: " + donationCreateRequest.getDocumentTypePatient()
                                + " - número: " + donationCreateRequest.getDocumentNumberPatient()
                );
            }

            patient = PatientEntity.builder().id(patientId).build();
        }

        BloodBankEntity bloodBank = BloodBankEntity.builder().id(donationCreateRequest.getBloodBankId()).build();

        DonationEntity newDonation = new DonationEntity();
        newDonation.setDonor(donor);
        newDonation.setPatient(patient);
        newDonation.setBloodBank(bloodBank);
        newDonation.setDonationPurpose(donationCreateRequest.getDonationPurpose());
        newDonation.setBloodComponent(donationCreateRequest.getBloodComponent());
        newDonation.setObservation(donationCreateRequest.getObservation());
        newDonation.setCreatedBy(UserUtils.getAuthenticatedUser());
        newDonation.setCreatedAt(LocalDateTime.now());
        newDonation.setStatus(DonationStatus.IN_PROCRESS.getLabel());
        newDonation.setInterrupted(false);

        DonationEntity savedDonation = donationRepository.save(newDonation);
        return savedDonation.getId();
    }

    @Override
    public DonationResponseDTO updateDonation(Long donationId, DonationUpdateDTO donationUpdateDTO) {
        DonationEntity donationEntity = donationRepository.findById(donationId)
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + donationId));

        if (donationUpdateDTO.getPatientId() == null) {
            donationEntity.setPatient(null);
        } else {
            PatientEntity patient = patientService.getPatientById(donationUpdateDTO.getPatientId());
            donationEntity.setPatient(patient);
        }
        donationMapper.updateEntityFromDto(donationUpdateDTO, donationEntity);

        donationEntity.setUpdatedAt(LocalDateTime.now());
        donationEntity.setUpdatedBy(UserUtils.getAuthenticatedUser());
        DonationEntity updated = donationRepository.save(donationEntity);

        return donationMapper.toResponseDto(updated);
    }

    @Override
    public Page<DonationByDonorDTO> getDonationsByDonor(String documentType,String documentNumber, int page, int size){
        Long donorId = donorService.getIdDonor(documentType, documentNumber);
        if (donorId != 0L) {
            Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<DonationEntity> donationsPage = donationRepository.findByDonorId(donorId, pageable);
            return donationsPage.map(donationMapper::toDonationByDonorDTO);
        }else{
            throw new IllegalArgumentException("Donante no encontrada con documento");
        }
    }


    @Override
    public boolean updateInterviewAnswer(Long donationId, Long interviewAnswerId) {
        return donationRepository.updateInterviewAnswer(donationId, interviewAnswerId) > 0;
    }

    @Override
    public boolean updatePhysicalAssessment(Long donationId, Long physicalAssessmentId) {
        return donationRepository.updatePhysicalAssessment(donationId, physicalAssessmentId) > 0;
    }

    @Override
    public boolean updateBloodExtraction(Long donationId, Long bloodExtractionId) {
        return donationRepository.updateBloodExtraction(donationId, bloodExtractionId) > 0;
    }
}
