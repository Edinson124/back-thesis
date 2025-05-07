package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.HematologicalTestEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Donation.Dto.DonationRelationsDTO;
import com.yawarSoft.Modules.Donation.Dto.HematologicalTestDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.HematologicalTestRequest;
import com.yawarSoft.Modules.Donation.Enums.DonationStatus;
import com.yawarSoft.Modules.Donation.Enums.HematologicalTestStatus;
import com.yawarSoft.Modules.Donation.Mappers.HematologicalMapper;
import com.yawarSoft.Modules.Donation.Repositories.HematologicalTestRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.HematologicalTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HematologicalTestServiceImpl implements HematologicalTestService {

    private final HematologicalTestRepository hematologicalTestRepository;
    private final HematologicalMapper hematologicalMapper;
    private final DonationService donationService;
    private final AuthenticatedUserService authenticatedUserService;

    public HematologicalTestServiceImpl(HematologicalTestRepository hematologicalTestRepository, HematologicalMapper hematologicalMapper, DonationService donationService, AuthenticatedUserService authenticatedUserService) {
        this.hematologicalTestRepository = hematologicalTestRepository;
        this.hematologicalMapper = hematologicalMapper;
        this.donationService = donationService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional
    @Override
    public Long createHematologicalTest(HematologicalTestRequest hematologicalTestRequest) {
        UserEntity userEntity = authenticatedUserService.getCurrentUser();
        Long donationId = hematologicalTestRequest.getDonationId();
        HematologicalTestEntity hematologicalTest = hematologicalMapper.toEntityByRequest(hematologicalTestRequest);
        hematologicalTest.setCreatedBy(userEntity);
        hematologicalTest.setCreatedAt(LocalDateTime.now());
        hematologicalTest.setStatus(HematologicalTestStatus.COMPLETED.getLabel());

        HematologicalTestEntity hematologicalTestSaved = hematologicalTestRepository.save(hematologicalTest);
        donationService.updateHematologicalTest(donationId,hematologicalTestSaved.getId());

        donationService.updateDonorBloodType(donationId,hematologicalTestRequest.getBloodType(),hematologicalTestRequest.getRhFactor());
        donationService.updateDonationFinishedById(donationId, DonationStatus.FINISHED.getLabel());
        return hematologicalTestSaved.getId();
    }

    @Override
    public HematologicalTestDTO getHematologicalTest(Long donationId) {
        DonationRelationsDTO relationsDTO = donationService.getIdsRelations(donationId);
        HematologicalTestEntity hematologicalTest = hematologicalTestRepository
                .findById(relationsDTO.getIdHematologicalTest())
                .orElse(null);

        if (hematologicalTest == null) {
            return null;
        }
        return hematologicalMapper.toDto(hematologicalTest);
    }
}
