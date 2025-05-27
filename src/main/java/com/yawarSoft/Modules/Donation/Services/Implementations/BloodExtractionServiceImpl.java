package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.BloodExtractionEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.BloodExtractionDTO;
import com.yawarSoft.Modules.Donation.Dto.DonationRelationsDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.BloodExtractionRequest;
import com.yawarSoft.Modules.Donation.Mappers.BloodExtractionMapper;
import com.yawarSoft.Modules.Donation.Repositories.BloodExtractionRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.BloodExtractionService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BloodExtractionServiceImpl implements BloodExtractionService {

    private final BloodExtractionRepository bloodExtractionRepository;
    private final BloodExtractionMapper bloodExtractionMapper;
    private final DonationService donationService;
    private final AuthenticatedUserService authenticatedUserService;

    public BloodExtractionServiceImpl(BloodExtractionRepository bloodExtractionRepository, BloodExtractionMapper bloodExtractionMapper, DonationService donationService, AuthenticatedUserService authenticatedUserService) {
        this.bloodExtractionRepository = bloodExtractionRepository;
        this.bloodExtractionMapper = bloodExtractionMapper;
        this.donationService = donationService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public BloodExtractionDTO createBloodExtraction(BloodExtractionRequest bloodExtractionRequest) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();

        // Convertir el request en una entidad
        BloodExtractionEntity entity = bloodExtractionMapper.toEntity(bloodExtractionRequest);

        entity.setCreatedBy(userAuth);
        entity.setCreatedAt(LocalDateTime.now());
        BloodExtractionEntity savedEntity = bloodExtractionRepository.save(entity);

        // Asociar el InterviewAnswer a la Donation
        donationService.updateBloodExtraction(bloodExtractionRequest.getDonationId(), savedEntity.getId());

        return bloodExtractionMapper.toDto(savedEntity);
    }

    @Override
    public BloodExtractionDTO updateBloodExtraction(Long id, BloodExtractionRequest bloodExtractionRequest) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();

        BloodExtractionEntity existingEntity = bloodExtractionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Extracción de sangre no encontrada con ID: " + id));

        bloodExtractionMapper.updateEntityFromDto(bloodExtractionRequest, existingEntity);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setUpdatedBy(userAuth);

        BloodExtractionEntity updatedEntity = bloodExtractionRepository.save(existingEntity);
        return bloodExtractionMapper.toDto(updatedEntity);
    }

    @Override
    public BloodExtractionDTO getBloodExtraction(Long idDonation) {
        DonationRelationsDTO relationsDTO = donationService.getIdsRelations(idDonation);
        if (relationsDTO.getIdBloodExtraction() == null) {return null;}
        Long id = relationsDTO.getIdBloodExtraction();
        BloodExtractionEntity entity = bloodExtractionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Extracción de sangre no encontrada con ID: " + id));
        return bloodExtractionMapper.toDto(entity);
    }
}
