package com.yawarSoft.Modules.Laboratory.Services.Implementations;

import com.yawarSoft.Core.Entities.SerologyTestEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Donation.Dto.DonationRelationsDTO;
import com.yawarSoft.Modules.Laboratory.Dto.Request.SerologyTestRequest;
import com.yawarSoft.Modules.Laboratory.Dto.SerologyTestDTO;
import com.yawarSoft.Modules.Donation.Enums.DonationStatus;
import com.yawarSoft.Modules.Laboratory.Enums.SerologyTestStatus;
import com.yawarSoft.Modules.Laboratory.Mappers.SerologyTestMapper;
import com.yawarSoft.Modules.Laboratory.Repositories.SerologyTestRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Laboratory.Services.Interfaces.SerologyTestService;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SerologyTestServiceImpl implements SerologyTestService {

    private final SerologyTestRepository serologyTestRepository;
    private final DonationService donationService;
    private final UnitService unitService;
    private final SerologyTestMapper serologyTestMapper;
    private final AuthenticatedUserService authenticatedUserService;

    public SerologyTestServiceImpl(SerologyTestRepository serologyTestRepository, DonationService donationService, UnitService unitService, SerologyTestMapper serologyTestMapper, AuthenticatedUserService authenticatedUserService) {
        this.serologyTestRepository = serologyTestRepository;
        this.donationService = donationService;
        this.unitService = unitService;
        this.serologyTestMapper = serologyTestMapper;
        this.authenticatedUserService = authenticatedUserService;
    }


    @Transactional
    @Override
    public Long createSerologyTest(SerologyTestRequest serologyTestRequest) {
        UserEntity userEntity = authenticatedUserService.getCurrentUser();
        Long donationId = serologyTestRequest.getDonationId();

        boolean isReactive = (
                Boolean.TRUE.equals(serologyTestRequest.getHiv()) ||
                        Boolean.TRUE.equals(serologyTestRequest.getHbsAg()) ||
                        Boolean.TRUE.equals(serologyTestRequest.getHbcAb()) ||
                        Boolean.TRUE.equals(serologyTestRequest.getHcv()) ||
                        Boolean.TRUE.equals(serologyTestRequest.getSyphilis()) ||
                        Boolean.TRUE.equals(serologyTestRequest.getChagas()) ||
                        Boolean.TRUE.equals(serologyTestRequest.getHtlvI_II())
        );

        String status = isReactive
                ? SerologyTestStatus.REACTIVE.getLabel()
                : SerologyTestStatus.NO_REACTIVE.getLabel();

        SerologyTestEntity serologyTestEntity = serologyTestMapper.toEntityByRequest(serologyTestRequest);
        serologyTestEntity.setCreatedBy(userEntity);
        serologyTestEntity.setCreatedAt(LocalDateTime.now());
        serologyTestEntity.setStatus(status);

        SerologyTestEntity result = serologyTestRepository.save(serologyTestEntity);
        donationService.updateSerologyTest(donationId, result.getId());

        if (isReactive) {
            donationService.updateDonationReactiveTestSeorologyById(donationId);
            unitService.updateUnitsReactiveTestSerologyById(donationId,SerologyTestStatus.REACTIVE.getLabel());

        }
        else{
            donationService.updateDonationFinishedById(donationId, DonationStatus.FINISHED.getLabel());
            unitService.updateUnitsNoReactiveTestSerologyById(donationId,SerologyTestStatus.NO_REACTIVE.getLabel());
        }
        return result.getId();
    }

    @Override
    public SerologyTestDTO getSerologyTest(Long donationId) {
        DonationRelationsDTO relationsDTO = donationService.getIdsRelations(donationId);
        if (relationsDTO.getIdSerologyTest() == null) {return null;}
        SerologyTestEntity serologyTestEntity = serologyTestRepository
                .findById(relationsDTO.getIdSerologyTest())
                .orElse(null);

        return serologyTestMapper.toDto(serologyTestEntity);
    }
}
