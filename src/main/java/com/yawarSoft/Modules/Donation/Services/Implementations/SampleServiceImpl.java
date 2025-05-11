package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.DonationEntity;
import com.yawarSoft.Core.Entities.SampleEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Donation.Dto.SampleDTO;
import com.yawarSoft.Modules.Donation.Enums.SampleStatus;
import com.yawarSoft.Modules.Donation.Mappers.SampleMapper;
import com.yawarSoft.Modules.Donation.Repositories.SampleRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.SampleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SampleServiceImpl implements SampleService {

    private final SampleRepository sampleRepository;
    private final SampleMapper sampleMapper;
    private final AuthenticatedUserService authenticatedUserService;

    public SampleServiceImpl(SampleRepository sampleRepository, SampleMapper sampleMapper, AuthenticatedUserService authenticatedUserService) {
        this.sampleRepository = sampleRepository;
        this.sampleMapper = sampleMapper;
        this.authenticatedUserService = authenticatedUserService;
    }


    @Override
    public Long createSample(Long idDonation, String test) {
        UserEntity userEntity = authenticatedUserService.getCurrentUser();
        DonationEntity donationEntity = new DonationEntity();
        donationEntity.setId(idDonation);
        SampleEntity sample = new SampleEntity();
        sample.setTest(test);
        sample.setStatus(SampleStatus.REGISTERED.getLabel());
        sample.setDonation(donationEntity);
        sample.setCreatedAt(LocalDateTime.now());
        sample.setCreatedBy(userEntity);

        SampleEntity sampleEntity = sampleRepository.save(sample);
        return sampleEntity.getId();
    }

    @Override
    public List<SampleDTO> getSamples(Long idDonation) {
        List<SampleEntity> samples = sampleRepository.findByDonationId(idDonation);
        return samples.stream()
                .map(sampleMapper::toDto)
                .collect(Collectors.toList());
    }

}
