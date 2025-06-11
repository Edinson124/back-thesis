package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.*;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Core.Utils.AESGCMEncryptionUtil;
import com.yawarSoft.Core.Utils.HmacUtil;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.*;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionAssignmentDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionRequestDetailDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionResultDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionViewDTO;
import com.yawarSoft.Modules.Transfusion.Enums.TransfusionStatus;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionAssignmentMapper;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionRequestDetailMapper;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionRequestMapper;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionResultMapper;
import com.yawarSoft.Modules.Transfusion.Repositories.PatientRepository;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionRequestRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionRequestService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransfusionRequestServiceImpl implements TransfusionRequestService {

    private final TransfusionRequestRepository transfusionRequestRepository;
    private final PatientRepository patientRepository;
    private final TransfusionRequestMapper transfusionRequestMapper;
    private final TransfusionAssignmentMapper transfusionAssignmentMapper;
    private final TransfusionRequestDetailMapper transfusionRequestDetailMapper;
    private final TransfusionResultMapper transfusionResultMapper;
    private final AuthenticatedUserService authenticatedUserService;
    private final HmacUtil hmacUtil;
    private final AESGCMEncryptionUtil aesGCMEncryptionUtil;

    public TransfusionRequestServiceImpl(TransfusionRequestRepository transfusionRequestRepository, PatientRepository patientRepository, TransfusionRequestMapper transfusionRequestMapper, TransfusionAssignmentMapper transfusionAssignmentMapper, TransfusionRequestDetailMapper transfusionRequestDetailMapper, TransfusionResultMapper transfusionResultMapper, AuthenticatedUserService authenticatedUserService, HmacUtil hmacUtil, AESGCMEncryptionUtil aesGCMEncryptionUtil) {
        this.transfusionRequestRepository = transfusionRequestRepository;
        this.patientRepository = patientRepository;
        this.transfusionRequestMapper = transfusionRequestMapper;
        this.transfusionAssignmentMapper = transfusionAssignmentMapper;
        this.transfusionRequestDetailMapper = transfusionRequestDetailMapper;
        this.transfusionResultMapper = transfusionResultMapper;
        this.authenticatedUserService = authenticatedUserService;
        this.hmacUtil = hmacUtil;
        this.aesGCMEncryptionUtil = aesGCMEncryptionUtil;
    }

    @Override
    public Page<TransfusionByPatientDTO> getTranfusionByPatient(String documentType, String documentNumber, int page, int size) {
        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        Long patientId = patientRepository.findIdBySearchHash(searchHash).orElse(0L);
        if (patientId != 0L) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            Page<TransfusionRequestEntity> transfusionPage = transfusionRequestRepository.findByPatientId(patientId, pageable);
            return transfusionPage.map(transfusionRequestMapper::toTransfusionByPatientDto);
        }else{
            throw new IllegalArgumentException("Donante no encontrada con documento");
        }
    }

    @Override
    public Page<TranfusionListDTO> getTransfusions(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, Long code, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        Integer bloodBankId = userAuthenticated.getBloodBank().getId();

        Specification<TransfusionRequestEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por banco de sangre
            predicates.add(cb.equal(root.get("bloodBank").get("id"), bloodBankId));

            // Filtro por fecha
            if (startEntryDate != null && endEntryDate != null) {
                predicates.add(cb.between(root.get("date"), startEntryDate, endEntryDate));
            } else if (startEntryDate != null) {
                predicates.add(cb.equal(root.get("date"), startEntryDate));
            } else if (endEntryDate != null) {
                predicates.add(cb.equal(root.get("date"), endEntryDate));
            }

            // Filtro por status (si viene)
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // Filtro por code (id) si viene
            if (code != null) {
                predicates.add(cb.equal(root.get("id"), code));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<TransfusionRequestEntity> pageResult = transfusionRequestRepository.findAll(spec, pageable);

        return pageResult.map(entity -> transfusionRequestMapper.toListDTO(entity, aesGCMEncryptionUtil));
    }

    @Override
    public ExistTransfusionDTO existsByCode(Long id) {
        ExistTransfusionDTO result = new ExistTransfusionDTO();
        TransfusionRequestEntity transfusionEntity = transfusionRequestRepository.findById(id).orElse(null);
        if (transfusionEntity == null) {
            result.setTransfusionActualExists(false);
            result.setCanViewTransfusion(false);
            result.setTransfusionId(null);
            result.setTransfusionResultId(null);
            result.setIsResultRegistrationAllowed(false);
        } else {
            UserEntity userEntity = authenticatedUserService.getCurrentUser();
            // Verificar si el banco de sangre de la donación es el mismo que el banco de sangre del usuario
            Boolean canViewTransfusion = transfusionEntity.getBloodBank().getId().equals(userEntity.getBloodBank().getId());
            result.setTransfusionActualExists(true);
            result.setCanViewTransfusion(canViewTransfusion);
            result.setTransfusionId(transfusionEntity.getId());
            result.setIsResultRegistrationAllowed(false);
            if(transfusionEntity.getStatus().equals(TransfusionStatus.LIBERADA.getLabel())){
                result.setIsResultRegistrationAllowed(true);
            }
            if (transfusionEntity.getTransfusionResult() != null) {
                result.setTransfusionResultId(transfusionEntity.getTransfusionResult().getId());
            } else {
                result.setTransfusionResultId(null);
            }
        }
        return result;
    }

    @Override
    public TransfusionDetailDTO getDetailTransfusion(Long id) {
        TransfusionRequestEntity transfusionEntity = transfusionRequestRepository.findById(id).orElse(null);
        return transfusionRequestMapper.toDetailDTO(transfusionEntity, aesGCMEncryptionUtil);
    }

    @Override
    public TransfusionGetDTO getTranfusion(Long id) {
        TransfusionGetDTO result = new TransfusionGetDTO();

        TransfusionRequestEntity transfusionEntity = transfusionRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud de transfusión no encontrada con ID: " + id));
        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();

        Boolean canViewTransfusion = userAuthenticated.getBloodBank() != null
                && transfusionEntity.getBloodBank().getId().equals(userAuthenticated.getBloodBank().getId());
        result.setCanViewTransfusion(canViewTransfusion);

        if (!canViewTransfusion) {
            result.setTransfusion(null);
            result.setResult(null);
            result.setRequest(null);
            result.setAssignments(null);
            return result;
        }
        TransfusionViewDTO transfusionViewDTO = transfusionRequestMapper.toTransfusionViewTO(transfusionEntity,aesGCMEncryptionUtil);
        result.setTransfusion(transfusionViewDTO);

        TransfusionResultEntity resultEntity = transfusionEntity.getTransfusionResult();
        TransfusionResultDTO resultDTO = resultEntity != null
                ? transfusionResultMapper.toDetailDTO(resultEntity)
                : null;
        result.setResult(resultDTO);

        List<TransfusionRequestDetailEntity> details = transfusionEntity.getDetails();
        List<TransfusionRequestDetailDTO> requestDetails = transfusionRequestDetailMapper.toTransfusionRequestDetailDtoList(details);
        result.setRequest(requestDetails);

        List<TransfusionAssignmentEntity> assignments = transfusionEntity.getDetailsAssignment();
        List<TransfusionAssignmentDTO> assignmentDTOs = transfusionAssignmentMapper.toTransfusionAssignmentDtoList(assignments);
        result.setAssignments(assignmentDTOs);

        return result;
    }

    @Override
    public Long createTransfusion(TransfusionRequestDTO transfusionRequestDTO) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();

        String documentType= transfusionRequestDTO.getPatientDocumentType();
        String documentNumber= transfusionRequestDTO.getPatientDocumentNumber();

        String combinedInfo = documentType + '|' + documentNumber;
        String searchHash = hmacUtil.generateHmac(combinedInfo);

        Long donorId = patientRepository.findIdBySearchHash(searchHash).orElse(0L);
        if (donorId == 0) {
            throw new IllegalArgumentException("Donante no encontrado con el documento tipo: " + documentType
                    + " - número: " + documentNumber);
        }

        PatientEntity patient = PatientEntity.builder().id(donorId).build();
        UserEntity attendingDoctor = UserEntity.builder().id(transfusionRequestDTO.getAttendingDoctor()).build();

        TransfusionRequestEntity transfusionRequestEntity = new TransfusionRequestEntity();
        transfusionRequestEntity.setAttendingDoctor(attendingDoctor);
        transfusionRequestEntity.setPatient(patient);
        transfusionRequestEntity.setBloodBank(userAuth.getBloodBank());
        transfusionRequestEntity.setBed(transfusionRequestDTO.getBed());
        transfusionRequestEntity.setMedicalService(transfusionRequestDTO.getMedicalService());
        transfusionRequestEntity.setHasCrossmatch(transfusionRequestDTO.getHasCrossmatch());
        transfusionRequestEntity.setDiagnosis(transfusionRequestDTO.getDiagnosis());
        transfusionRequestEntity.setRequestReason(transfusionRequestDTO.getRequestReason());
        transfusionRequestEntity.setStatus(TransfusionStatus.PENDIENTE.getLabel());
        transfusionRequestEntity.setDate(LocalDate.now());

        transfusionRequestEntity.setCreatedBy(userAuth);
        transfusionRequestEntity.setCreatedAt(LocalDateTime.now());

        List<TransfusionRequestDetailEntity> details = transfusionRequestDetailMapper.
                toEntityByRequestCreate(transfusionRequestDTO.getRequest());

        for (TransfusionRequestDetailEntity detail : details) {
            detail.setCreatedBy(userAuth);
            detail.setCreatedAt(LocalDateTime.now());
            detail.setTransfusionRequest(transfusionRequestEntity);
        }
        transfusionRequestEntity.setDetails(details);
        TransfusionRequestEntity transfusionSaved = transfusionRequestRepository.save(transfusionRequestEntity);
        return transfusionSaved.getId();
    }

    @Override
    public Long editTransfusion(TransfusionRequestDTO transfusionRequestDTO) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();

        TransfusionRequestEntity transfusionRequest = transfusionRequestRepository.findById(transfusionRequestDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Transfusion no encontrada"));

        UserEntity attendingDoctor = UserEntity.builder().id(transfusionRequestDTO.getAttendingDoctor()).build();
        transfusionRequest.setAttendingDoctor(attendingDoctor);
        transfusionRequest.setBed(transfusionRequestDTO.getBed());
        transfusionRequest.setMedicalService(transfusionRequestDTO.getMedicalService());
        transfusionRequest.setHasCrossmatch(transfusionRequestDTO.getHasCrossmatch());
        transfusionRequest.setDiagnosis(transfusionRequestDTO.getDiagnosis());
        transfusionRequest.setRequestReason(transfusionRequestDTO.getRequestReason());

        transfusionRequest.setUpdatedBy(userAuth);
        transfusionRequest.setUpdatedAt(LocalDateTime.now());

        // === MANEJO DE DETAILS ===
        // IDs actuales en base de datos
        Map<Long, TransfusionRequestDetailEntity> currentDetailMap = transfusionRequest.getDetails().stream()
                .collect(Collectors.toMap(TransfusionRequestDetailEntity::getId, d -> d));

        // IDs recibidos en DTO
        Set<Long> dtoIds = transfusionRequestDTO.getRequest().stream()
                .map(TransfusionRequestDetailDTO::getId)
                .collect(Collectors.toSet());

        // Lista final de detalles
        List<TransfusionRequestDetailEntity> finalDetails = new ArrayList<>();

        for (TransfusionRequestDetailDTO dtoDetail : transfusionRequestDTO.getRequest()) {
            TransfusionRequestDetailEntity detailEntity = currentDetailMap.get(dtoDetail.getId());

            if (detailEntity != null) {
                // Ya existe, actualizar
                detailEntity.setUnitType(dtoDetail.getUnitType());
                detailEntity.setRequestedQuantity(dtoDetail.getRequestedQuantity());
                finalDetails.add(detailEntity);
            } else {
                // No existe, crear nuevo (aunque tenga ID)
                TransfusionRequestDetailEntity newDetail = TransfusionRequestDetailEntity.builder()
                        .unitType(dtoDetail.getUnitType())
                        .requestedQuantity(dtoDetail.getRequestedQuantity())
                        .createdBy(userAuth)
                        .createdAt(LocalDateTime.now())
                        .transfusionRequest(transfusionRequest)
                        .build();
                finalDetails.add(newDetail);
            }
        }
        transfusionRequest.getDetails().clear();
        transfusionRequest.getDetails().addAll(finalDetails);
        TransfusionRequestEntity saved = transfusionRequestRepository.save(transfusionRequest);
        return saved.getId();
    }
}
