package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.PhysicalAssessmentEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.PhysicalAssessmentDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.PhysicalAssessmentRequest;
import com.yawarSoft.Modules.Donation.Mappers.PhysicalAssesmentMapper;
import com.yawarSoft.Modules.Donation.Repositories.PhysicalAssessmentRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.PhysicalAssessmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PhysicalAssessmentServiceImpl implements PhysicalAssessmentService {

    private final PhysicalAssessmentRepository physicalAssessmentRepository;
    private final PhysicalAssesmentMapper physicalAssessmentMapper;
    private final DonationService donationService;

    public PhysicalAssessmentServiceImpl(PhysicalAssessmentRepository physicalAssessmentRepository, PhysicalAssesmentMapper physicalAssessmentMapper, DonationService donationService) {
        this.physicalAssessmentRepository = physicalAssessmentRepository;
        this.physicalAssessmentMapper = physicalAssessmentMapper;
        this.donationService = donationService;
    }


    @Override
    public PhysicalAssessmentDTO createPhysicalAssessment(PhysicalAssessmentRequest physicalAssessmentRequest) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();
        PhysicalAssessmentEntity physicalAssessment = physicalAssessmentMapper.toEntityByRequest(physicalAssessmentRequest);
        physicalAssessment.setCreatedAt(LocalDateTime.now());
        physicalAssessment.setCreatedBy(userAuth);
        PhysicalAssessmentEntity savedEntity = physicalAssessmentRepository.save(physicalAssessment);
        donationService.updatePhysicalAssessment(physicalAssessmentRequest.getDonationId(), savedEntity.getId());
        return physicalAssessmentMapper.toDto(savedEntity);
    }

    @Override
    public PhysicalAssessmentDTO updatePhysicalAssessment(Long id, PhysicalAssessmentRequest physicalAssessmentRequest) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();

        PhysicalAssessmentEntity existingEntity = physicalAssessmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evaluación física no encontrada con ID: " + id));

        physicalAssessmentMapper.updateEntityFromDto(physicalAssessmentRequest, existingEntity);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setUpdatedBy(userAuth);

        physicalAssessmentRepository.save(existingEntity);
        return physicalAssessmentMapper.toDto(existingEntity);
    }

    @Override
    public PhysicalAssessmentDTO getPhysicalAssessment(Long id) {
        PhysicalAssessmentEntity entity = physicalAssessmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evaluación física no encontrada con ID: " + id));
        return physicalAssessmentMapper.toDto(entity);
    }
}
