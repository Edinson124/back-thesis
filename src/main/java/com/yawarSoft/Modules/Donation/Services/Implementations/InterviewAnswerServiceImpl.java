package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.InterviewAnswerEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Donation.Dto.InterviewAnswerDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.InterviewAnswerRequest;
import com.yawarSoft.Modules.Donation.Mappers.InterviewAnswerMapper;
import com.yawarSoft.Modules.Donation.Repositories.InterviewAnswerRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.InterviewAnswerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InterviewAnswerServiceImpl implements InterviewAnswerService {

    private final InterviewAnswerRepository interviewAnswerRepository;
    private final InterviewAnswerMapper interviewAnswerMapper;
    private final DonationService donationService;

    public InterviewAnswerServiceImpl(InterviewAnswerRepository interviewAnswerRepository, InterviewAnswerMapper interviewAnswerMapper, DonationService donationService) {
        this.interviewAnswerRepository = interviewAnswerRepository;
        this.interviewAnswerMapper = interviewAnswerMapper;
        this.donationService = donationService;
    }

    @Override
    public InterviewAnswerDTO createInterviewAnswer(InterviewAnswerRequest interviewAnswerRequest) {
        UserEntity userAuth = UserUtils.getAuthenticatedUser();

        // Convertir el request en una entidad
        InterviewAnswerEntity entity = interviewAnswerMapper.toEntity(interviewAnswerRequest);

        entity.setCreatedBy(userAuth);
        entity.setCreatedAt(LocalDateTime.now());
        InterviewAnswerEntity savedEntity = interviewAnswerRepository.save(entity);

        // Asociar el InterviewAnswer a la Donation
        donationService.updateInterviewAnswer(interviewAnswerRequest.donationId(), savedEntity.getId());

        return interviewAnswerMapper.toDto(savedEntity);
    }

    @Override
    public InterviewAnswerEntity getInterviewAnswer(Long id) {
        return interviewAnswerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrevista no encontrada con ID: " + id));
    }
}
