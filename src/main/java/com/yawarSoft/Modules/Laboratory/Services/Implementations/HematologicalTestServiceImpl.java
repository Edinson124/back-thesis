package com.yawarSoft.Modules.Laboratory.Services.Implementations;

import com.yawarSoft.Core.Entities.HematologicalTestEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Donation.Dto.DonationRelationsDTO;
import com.yawarSoft.Modules.Laboratory.Dto.HematologicalTestDTO;
import com.yawarSoft.Modules.Laboratory.Dto.Request.HematologicalTestRequest;
import com.yawarSoft.Modules.Donation.Enums.DonationStatus;
import com.yawarSoft.Modules.Laboratory.Enums.HematologicalTestStatus;
import com.yawarSoft.Modules.Donation.Enums.RhFactor;
import com.yawarSoft.Modules.Laboratory.Mappers.HematologicalMapper;
import com.yawarSoft.Modules.Laboratory.Repositories.HematologicalTestRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Laboratory.Services.Interfaces.HematologicalTestService;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HematologicalTestServiceImpl implements HematologicalTestService {

    private final HematologicalTestRepository hematologicalTestRepository;
    private final HematologicalMapper hematologicalMapper;
    private final DonationService donationService;
    private final AuthenticatedUserService authenticatedUserService;
    private final UnitService unitService;

    public HematologicalTestServiceImpl(HematologicalTestRepository hematologicalTestRepository, HematologicalMapper hematologicalMapper, DonationService donationService, AuthenticatedUserService authenticatedUserService, UnitService unitService) {
        this.hematologicalTestRepository = hematologicalTestRepository;
        this.hematologicalMapper = hematologicalMapper;
        this.donationService = donationService;
        this.authenticatedUserService = authenticatedUserService;
        this.unitService = unitService;
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

        String rh = RhFactor.getSymbolByName(hematologicalTestRequest.getRhFactor());
        unitService.updateBloodTypeIfHematologicalTestAfter(donationId, hematologicalTestRequest.getBloodType()+rh);
        return hematologicalTestSaved.getId();
    }

    @Override
    public HematologicalTestDTO getHematologicalTest(Long donationId) {
        DonationRelationsDTO relationsDTO = donationService.getIdsRelations(donationId);
        if(relationsDTO.getIdHematologicalTest() == null){ return null;}
        HematologicalTestEntity hematologicalTest = hematologicalTestRepository
                .findById(relationsDTO.getIdHematologicalTest())
                .orElse(null);
        return hematologicalMapper.toDto(hematologicalTest);
    }
}
