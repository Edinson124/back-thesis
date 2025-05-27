package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.InterviewAnswerEntity;
import com.yawarSoft.Core.Entities.InterviewQuestionStructureEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Donation.Dto.DonationRelationsDTO;
import com.yawarSoft.Modules.Donation.Dto.InterviewAnswerDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.InterviewAnswerRequest;
import com.yawarSoft.Modules.Donation.Dto.Response.InterviewQuestionStructureDTO;
import com.yawarSoft.Modules.Donation.Mappers.InterviewAnswerMapper;
import com.yawarSoft.Modules.Donation.Repositories.InterviewAnswerRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.InterviewAnswerService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.InterviewQuestionStructureService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InterviewAnswerServiceImpl implements InterviewAnswerService {

    private final InterviewAnswerRepository interviewAnswerRepository;
    private final InterviewAnswerMapper interviewAnswerMapper;
    private final DonationService donationService;
    private final InterviewQuestionStructureService interviewQuestionStructureService;
    private final AuthenticatedUserService authenticatedUserService;

    public InterviewAnswerServiceImpl(InterviewAnswerRepository interviewAnswerRepository, InterviewAnswerMapper interviewAnswerMapper, DonationService donationService, InterviewQuestionStructureService interviewQuestionStructureService, AuthenticatedUserService authenticatedUserService) {
        this.interviewAnswerRepository = interviewAnswerRepository;
        this.interviewAnswerMapper = interviewAnswerMapper;
        this.donationService = donationService;
        this.interviewQuestionStructureService = interviewQuestionStructureService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public InterviewAnswerDTO createInterviewAnswer(InterviewAnswerRequest interviewAnswerRequest) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        InterviewQuestionStructureDTO interviewQuestionStructure = interviewQuestionStructureService.getActualInterviewStructure();

        // Convertir el request en una entidad
        InterviewAnswerEntity entity = new InterviewAnswerEntity();
        entity.setInterviewQuestion(InterviewQuestionStructureEntity.builder().
                id(interviewQuestionStructure.getId()).build());
        entity.setAnswer(interviewAnswerRequest.answer());
        entity.setCreatedBy(userAuth);
        entity.setCreatedAt(LocalDateTime.now());
        InterviewAnswerEntity savedEntity = interviewAnswerRepository.save(entity);

        // Asociar el InterviewAnswer a la Donation
        donationService.updateInterviewAnswer(interviewAnswerRequest.donationId(), savedEntity.getId());

        return interviewAnswerMapper.toDto(savedEntity);
    }

    @Override
    public InterviewAnswerDTO getInterviewAnswer(Long idDonation) {
        DonationRelationsDTO relationsDTO = donationService.getIdsRelations(idDonation);
        if (relationsDTO.getIdInterviewAnswer() == null) {return null;}
        Long id = relationsDTO.getIdInterviewAnswer();
        InterviewAnswerEntity interviewAnswer = interviewAnswerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrevista no encontrada con ID: " + id));
        return interviewAnswerMapper.toDto(interviewAnswer);
    }
}
