package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Core.Entities.TransfusionAssignmentEntity;
import com.yawarSoft.Core.Entities.TransfusionRequestEntity;
import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Storage.Enums.UnitStatus;
import com.yawarSoft.Modules.Storage.Service.Interfaces.BloodStorageService;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import com.yawarSoft.Modules.Transfusion.Dto.Request.DispensedAssignUnitRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionAssignResultDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionAssignmentDTO;
import com.yawarSoft.Modules.Transfusion.Enums.TransfusionAssingmentResult;
import com.yawarSoft.Modules.Transfusion.Enums.TransfusionAssingmentStatus;
import com.yawarSoft.Modules.Transfusion.Enums.TransfusionStatus;
import com.yawarSoft.Modules.Transfusion.Mappers.TransfusionAssignmentMapper;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionAssignmentRepository;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionRequestRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionAssignmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransfusionAssignmentServiceImpl implements TransfusionAssignmentService {

    private final TransfusionRequestRepository transfusionRequestRepository;
    private final TransfusionAssignmentRepository transfusionAssignmentRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final UnitService unitService;
    private final TransfusionAssignmentMapper transfusionAssignmentMapper;
    private final BloodStorageService bloodStorageService;

    public TransfusionAssignmentServiceImpl(TransfusionRequestRepository transfusionRequestRepository, TransfusionAssignmentRepository transfusionAssignmentRepository, AuthenticatedUserService authenticatedUserService, UnitService unitService, TransfusionAssignmentMapper transfusionAssignmentMapper, BloodStorageService bloodStorageService) {
        this.transfusionRequestRepository = transfusionRequestRepository;
        this.transfusionAssignmentRepository = transfusionAssignmentRepository;
        this.authenticatedUserService = authenticatedUserService;
        this.unitService = unitService;
        this.transfusionAssignmentMapper = transfusionAssignmentMapper;
        this.bloodStorageService = bloodStorageService;
    }


    @Transactional
    @Override
    public TransfusionAssignmentDTO saveTransfusionAssignment(Long idTransfusion, Long idUnit) {
        TransfusionRequestEntity transfusionRequest = transfusionRequestRepository.findById(idTransfusion)
                .orElseThrow(() -> new IllegalArgumentException("Transfusión no encontrada con ID: " + idTransfusion));
        UserEntity userAuth = authenticatedUserService.getCurrentUser();

        UnitEntity unitEntity = UnitEntity.builder().id(idUnit).build();
        TransfusionAssignmentEntity transfusionAssignmentEntity = new TransfusionAssignmentEntity();
        transfusionAssignmentEntity.setTransfusionRequest(transfusionRequest);
        transfusionAssignmentEntity.setBloodUnit(unitEntity);
        transfusionAssignmentEntity.setStatus(TransfusionAssingmentStatus.PENDING.getLabel());
        transfusionAssignmentEntity.setCreatedBy(userAuth);
        transfusionAssignmentEntity.setCreatedAt(LocalDateTime.now());

        TransfusionAssignmentEntity assignSaved = transfusionAssignmentRepository.save(transfusionAssignmentEntity);
        TransfusionAssignmentEntity assignFinal = transfusionAssignmentRepository
                .findByIdWithUnit(assignSaved.getId())
                .orElseThrow(() -> new RuntimeException("No se encontró la asignación"));
        unitService.updateStatusUnit(idUnit, UnitStatus.RESERVED.getLabel());
        bloodStorageService.minusBloodStorage(userAuth.getBloodBank().getId(),assignFinal.getBloodUnit().getUnitType(),1);
        return transfusionAssignmentMapper.toTransfusionAssignmentDto(assignFinal);
    }

    @Transactional
    @Override
    public Long deleteTransfusionAssignment(Long idTransfusionAssignment) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        TransfusionAssignmentEntity transfusionAssignment = transfusionAssignmentRepository.findById(idTransfusionAssignment)
                .orElseThrow(() -> new IllegalArgumentException("Transfusión no encontrada con ID: " + idTransfusionAssignment));

        Long idUnit = transfusionAssignment.getBloodUnit().getId();
        transfusionAssignmentRepository.deleteById(idTransfusionAssignment);

        unitService.updateStatusUnit(idUnit, UnitStatus.SUITABLE.getLabel());
        bloodStorageService.addBloodStorage(userAuth.getBloodBank().getId(),
                transfusionAssignment.getBloodUnit().getUnitType(),1);
        return transfusionAssignment.getId();
    }


    @Transactional
    @Override
    public TransfusionAssignmentDTO saveValidateResult(Long idTransfusionAssignment, TransfusionAssignResultDTO request) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        TransfusionAssignmentEntity transfusionAssignment = transfusionAssignmentRepository.findById(idTransfusionAssignment)
                .orElseThrow(() -> new IllegalArgumentException("Transfusión no encontrada con ID: " + idTransfusionAssignment));

        String resultInitial = transfusionAssignment.getValidateResult();
        String resultString = request.getType()
                ? TransfusionAssingmentResult.COMPATIBLE.getLabel()
                : TransfusionAssingmentResult.INCOMPATIBLE.getLabel();

        String assignStatus = request.getType()
                ? TransfusionAssingmentStatus.APPROVED.getLabel()
                : TransfusionAssingmentStatus.REJECTED.getLabel();

        transfusionAssignment.setObservationTest(request.getObservation());
        transfusionAssignment.setPerformedTestBy(userAuth);
        transfusionAssignment.setValidateResultDate(LocalDateTime.now());
        transfusionAssignment.setValidateResult(resultString);
        transfusionAssignment.setStatus(assignStatus);
        transfusionAssignmentRepository.save(transfusionAssignment);

        Long idUnit = transfusionAssignment.getBloodUnit().getId();
        UnitEntity unitEntity = transfusionAssignment.getBloodUnit();
        if (request.getType()) {
            unitService.updateStatusUnit(idUnit, UnitStatus.RESERVED.getLabel());
            unitEntity.setStatus(UnitStatus.RESERVED.getLabel());
            if(resultInitial!= null) {
                bloodStorageService.minusBloodStorage(userAuth.getBloodBank().getId(), unitEntity.getUnitType(), 1);
            }
        } else {
            unitService.updateStatusUnit(idUnit, UnitStatus.SUITABLE.getLabel());
            unitEntity.setStatus(UnitStatus.SUITABLE.getLabel());
            bloodStorageService.addBloodStorage(userAuth.getBloodBank().getId(), unitEntity.getUnitType(),1);
        }
        transfusionAssignment.setBloodUnit(unitEntity);

        return transfusionAssignmentMapper.toTransfusionAssignmentDto(transfusionAssignment);
    }

    @Transactional
    @Override
    public Long dispensedUnits(Long idTransfusion, DispensedAssignUnitRequestDTO requestDTO) {
        TransfusionRequestEntity transfusionRequest = transfusionRequestRepository.findById(idTransfusion)
                .orElseThrow(() -> new IllegalArgumentException("Transfusión no encontrada con ID: " + idTransfusion));

        List<TransfusionAssignmentEntity> assignmentEntities = transfusionRequest.getDetailsAssignment();
        LocalDateTime now = LocalDateTime.now();
        UserEntity userAuth = authenticatedUserService.getCurrentUser();

        for (TransfusionAssignmentEntity assignment : assignmentEntities) {
            if (TransfusionAssingmentResult.COMPATIBLE.getLabel().equalsIgnoreCase(assignment.getValidateResult())) {
                // Actualizar unidad
                unitService.updateStatusUnit(assignment.getBloodUnit().getId(), UnitStatus.TRANSFUSED.getLabel());
                // Actualizar asignación
                assignment.setReceivedByDocument(requestDTO.getReceivedByDocument());
                assignment.setReceivedByName(requestDTO.getReceivedByName());
                assignment.setDispensedBy(userAuth);
                assignment.setDispensedDate(now);
                assignment.setStatus(TransfusionAssingmentStatus.DISPATCHED.getLabel());
                transfusionAssignmentRepository.save(assignment);
            }
        }
        transfusionRequest.setStatus(TransfusionStatus.LIBERADA.getLabel());
        transfusionRequestRepository.save(transfusionRequest);
        return transfusionRequest.getId();
    }

}
