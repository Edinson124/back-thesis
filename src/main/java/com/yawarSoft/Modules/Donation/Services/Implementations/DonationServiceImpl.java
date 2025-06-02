package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.*;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.Constants;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.*;
import com.yawarSoft.Modules.Donation.Dto.Request.DeferralDonationRequest;
import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.Response.DateDonationDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.ExistDonationDTO;
import com.yawarSoft.Modules.Donation.Enums.*;
import com.yawarSoft.Modules.Donation.Mappers.DonationMapper;
import com.yawarSoft.Modules.Donation.Mappers.DonorMapper;
import com.yawarSoft.Modules.Donation.Repositories.DonationRepository;
import com.yawarSoft.Modules.Donation.Repositories.DonorRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonorService;
import com.yawarSoft.Modules.Laboratory.Enums.SerologyTestStatus;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final DonationMapper donationMapper;
    private final DonorMapper donorMapper;
    private final PatientService patientService;
    private final DonorService donorService;
    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;
    private final AuthenticatedUserService authenticatedUserService;


    public DonationServiceImpl(DonationRepository donationRepository, DonorRepository donorRepository, DonationMapper donationMapper, DonorMapper donorMapper,
                               PatientService patientService, DonorService donorService, AESGCMEncryptionUtil aesGCMEncryptionUtil, AuthenticatedUserService authenticatedUserService) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.donationMapper = donationMapper;
        this.donorMapper = donorMapper;
        this.patientService = patientService;
        this.donorService = donorService;
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    @Transactional
    public Long createDonation(DonationCreateRequest donationCreateRequest) {
        UserEntity userEntity = authenticatedUserService.getCurrentUser();

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

        DonationEntity newDonation = new DonationEntity();
        newDonation.setDonor(donor);
        newDonation.setPatient(patient);
        newDonation.setBloodBank(userEntity.getBloodBank());
        newDonation.setDonationPurpose(donationCreateRequest.getDonationPurpose());
        newDonation.setBloodComponent(donationCreateRequest.getBloodComponent());
        newDonation.setObservation(donationCreateRequest.getObservation());
        newDonation.setCreatedBy(userEntity);
        newDonation.setCreatedAt(LocalDateTime.now());
        newDonation.setStatus(DonationStatus.IN_PROCRESS.getLabel());
        newDonation.setInterrupted(false);
        newDonation.setDate(LocalDate.now());

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

        return donationMapper.toResponseDto(updated,aesGCMEncryptionUtil);
    }

    @Override
    public Page<DonationByDonorDTO> getDonationsByDonor(String documentType,String documentNumber, int page, int size){
        Long donorId = donorService.getIdDonor(documentType, documentNumber);
        if (donorId != 0L) {
            Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "id"));
            Page<DonationEntity> donationsPage = donationRepository.findByDonorId(donorId, pageable);
            return donationsPage.map(donationMapper::toDonationByDonorDTO);
        }else{
            throw new IllegalArgumentException("Donante no encontrada con documento");
        }
    }

    @Override
    public DonationResponseDTO getActualDonation(String documentType, String documentNumber) {
        Long donorId = donorService.getIdDonor(documentType, documentNumber);
        Optional<DonationEntity> activeDonation = donationRepository.findByDonorIdAndStatus(
                donorId, DonationStatus.IN_PROCRESS.getLabel());

        return activeDonation
                .map(donation -> donationMapper.toResponseDto(donation, aesGCMEncryptionUtil))
                .orElse(null);
    }

    @Override
    public DateDonationDTO getDateDetailLastDonation(String documentType, String documentNumber) {
        Long idDonor = donorService.getIdDonor(documentType, documentNumber);
        DonorGetDTO donor = donorService.getDonor(documentType, documentNumber);
        Optional<DonationEntity> donation = donationRepository.findTopByDonorIdOrderByIdDesc(idDonor);

        if (donation.isEmpty()) {
            return null;
        }
        DonationEntity donationEntity = donation.get();
        LocalDate dateDonation = donationEntity.getDate();

        int requiredMonths = donor.getGender().equalsIgnoreCase(DonorGender.MASCULINO.getLabel())
                ? Constants.DONATION_INTERVAL_MALE_MONTHS
                : Constants.DONATION_INTERVAL_FEMALE_MONTHS;
        LocalDate dateEnabledDonation = dateDonation.plusMonths(requiredMonths);
        boolean isEnableDonation = !LocalDate.now().isBefore(dateEnabledDonation);
        boolean requiredAdvertisement = calculateAdvertisement(donor.getStatus(),isEnableDonation);

        DateDonationDTO dto = new DateDonationDTO();
        dto.setDonationId(donationEntity.getId());
        dto.setDateDonation(dateDonation);
        dto.setDateEnabledDonation(dateEnabledDonation);
        dto.setIsEnableDonation(isEnableDonation);
        dto.setRequiredAdvertisement(requiredAdvertisement);

        return dto;
    }
    private boolean calculateAdvertisement(String status, Boolean isEnableDonation) {
        if (!status.equals(DonorStatus.ELIGIBLE.getLabel())) return false;
        return !isEnableDonation;
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

    @Override
    public boolean updateSerologyTest(Long donationId, Long serologyTestId) {
        return donationRepository.updateSerologyTest(donationId, serologyTestId) > 0;
    }

    @Override
    public boolean updateHematologicalTest(Long donationId, Long hematologicalTestId) {
        return donationRepository.updateHematologicalTest(donationId, hematologicalTestId) > 0;
    }

    @Override
    public ExistDonationDTO existsByCode(Long id) {
        ExistDonationDTO result = new ExistDonationDTO();
        DonationEntity donationEntity = donationRepository.findById(id).orElse(null);
        if (donationEntity == null) {
            result.setDonationActualExists(false);
            result.setCanViewDonation(false);
            result.setDonationId(null);
        } else {
            UserEntity userEntity = authenticatedUserService.getCurrentUser();
            // Verificar si el banco de sangre de la donación es el mismo que el banco de sangre del usuario
            Boolean canViewDonation = donationEntity.getBloodBank().getId().equals(userEntity.getBloodBank().getId());
            result.setDonationActualExists(true);
            result.setCanViewDonation(canViewDonation);
            result.setDonationId(donationEntity.getId());
        }
        return result;
    }

    @Override
    public ExistDonationDTO existsActualByDonor(String documentType, String documentNumber) {
        ExistDonationDTO result = new ExistDonationDTO();
        Long donorId = donorService.getIdDonor(documentType,documentNumber);
        if(donorId == 0) {
            result.setExistDonor(false);
            result.setDonationActualExists(false);
            result.setCanViewDonation(false);
            result.setDonationId(null);
            return result;
        }
        result.setExistDonor(true);
        DonationResponseDTO donationResponseDTO = getActualDonation(documentType,documentNumber);
        if (donationResponseDTO == null) {
            result.setDonationActualExists(false);
            result.setCanViewDonation(false);
            result.setDonationId(null);
        } else {
            UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
            // Verificar si el banco de sangre de la donación es el mismo que el banco de sangre del usuario
            Boolean canViewDonation = userAuthenticated.getBloodBank() != null
                    && donationResponseDTO.getBloodBankId().equals(userAuthenticated.getBloodBank().getId());
            result.setDonationActualExists(true);
            result.setCanViewDonation(canViewDonation);
            result.setDonationId(donationResponseDTO.getId());
        }
        return result;
    }

    @Override
    public DonationGetDTO getDonationById(Long id) {
        DonationGetDTO result = new DonationGetDTO();

        DonationEntity donationEntity = donationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + id));
        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();

        Boolean canViewDonation = userAuthenticated.getBloodBank() != null
                && donationEntity.getBloodBank().getId().equals(userAuthenticated.getBloodBank().getId());
        result.setCanViewDonation(canViewDonation);

        if (!canViewDonation) {
            result.setDonor(null);
            result.setDonation(null);
            return result;
        }

        DonorGetDTO donorGetDTO = donorMapper.toGetDto(donationEntity.getDonor(),aesGCMEncryptionUtil);
        result.setDonor(donorGetDTO);
        DonationViewDTO donationViewDTO = donationMapper.toDonationViewDTO(donationEntity,aesGCMEncryptionUtil);
        result.setDonation(donationViewDTO);

        return result;
    }

    @Override
    public boolean updateDonationReactiveTestSeorologyById(Long id) {
        DonationEntity donationEntity = donationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + id));

        donationEntity.setStatus(DonationStatus.FINISHED_PERM_DEFER.getLabel());
        donationRepository.save(donationEntity);
        donorService.updateDonorReactiveTestSeorologyById(donationEntity.getDonor().getId());
        return true;
    }

    @Override
    public boolean updateDonationFinishedById(Long id, String status) {
        DonationEntity donationEntity = donationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + id));

        if(donationEntity.getHematologicalTest() != null && donationEntity.getSerologyTest() != null
                && donationEntity.getBloodExtraction() != null) {
            donationEntity.setStatus(status);
            donationRepository.save(donationEntity);
        }
        return true;
    }

    @Override
    public DonationRelationsDTO getIdsRelations(Long id) {
        DonationEntity donationEntity = donationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + id));
        return donationMapper.toDonationRelationsDTO(donationEntity);
    }

    @Override
    public boolean updateDonorBloodType(Long donationId, String bloodType, String rhFactor) {
        DonationEntity donationEntity = donationRepository.findById(donationId)
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + donationId));
        Long donorId = donationEntity.getDonor().getId();
        donorService.updateDonorBloodType(donorId,bloodType,rhFactor);
        return true;
    }

    @Override
    public Map<String, String> getBloodTypeAndSerology(Long id) {
        Map<String, String> result = new HashMap<>();
        DonationEntity donationEntity = donationRepository.findById(id).orElse(null);

        if (donationEntity != null) {
            String bloodType = null;

            if (donationEntity.getDonor() != null && donationEntity.getDonor().getBloodType() != null) {
                String type = donationEntity.getDonor().getBloodType();
                String rh = RhFactor.getSymbolByName(donationEntity.getDonor().getRhFactor());
                bloodType = type + rh;
            } else if (donationEntity.getHematologicalTest() != null && donationEntity.getHematologicalTest().getBloodType() != null) {
                String type = donationEntity.getHematologicalTest().getBloodType();
                String rh = RhFactor.getSymbolByName(donationEntity.getHematologicalTest().getRhFactor());
                bloodType = type + rh;
            }

            result.put("bloodType", bloodType);

            String serologyResult = donationEntity.getSerologyTest() != null
                    ? donationEntity.getSerologyTest().getStatus()
                    : SerologyTestStatus.PENDING.getLabel();
            result.put("serologyResult", serologyResult);
        }

        return result;
    }

    @Transactional
    @Override
    public Long finishDonationWithDeferral(Long idDonation, DeferralDonationRequest deferralDonationRequest) {
        DonationEntity donationEntity = donationRepository.findById(idDonation)
                .orElseThrow(() -> new IllegalArgumentException("Donación no encontrada con ID: " + idDonation));

        Long idDonor = donationEntity.getDonor().getId();
        String deferralReason = deferralDonationRequest.getReason();
        DeferralType deferralType;

        try {
            deferralType = DeferralType.valueOf(deferralReason);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de deferral inválido: " + deferralReason);
        }

        String donorStatus;
        String donationStatus;
        LocalDate deferralEndDate = null;

        switch (deferralType) {
            case PERMANENTE -> {
                donorStatus = DonorStatus.PERMANENTLY_DEFERRED.getLabel();
                donationStatus = DonationStatus.FINISHED_PERM_DEFER.getLabel();
            }
            case TEMPORAL -> {
                donorStatus = DonorStatus.TEMPORARILY_DEFERRED.getLabel();
                donationStatus = DonationStatus.FINISHED_TEMP_DEFER.getLabel();
                deferralEndDate = LocalDate.now().plusDays(deferralDonationRequest.getDays());
            }
            default -> throw new IllegalArgumentException("Tipo de deferral no soportado: " + deferralType);
        }

        donationEntity.setStatus(donationStatus);
        donationRepository.save(donationEntity);

        donorRepository.updateDonorDeferralById(idDonor, donorStatus, deferralEndDate, deferralReason);
        return  idDonation;
    }



}
