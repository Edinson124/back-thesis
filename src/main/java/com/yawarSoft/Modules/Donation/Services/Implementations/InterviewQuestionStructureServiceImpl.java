package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.InterviewQuestionStructureEntity;
import com.yawarSoft.Modules.Donation.Dto.Response.InterviewQuestionStructureDTO;
import com.yawarSoft.Modules.Donation.Enums.InterviewQuestionsStructureStatus;
import com.yawarSoft.Modules.Donation.Repositories.InterviewQuestionStructureRepository;
import com.yawarSoft.Modules.Donation.Services.Interfaces.InterviewQuestionStructureService;
import org.springframework.stereotype.Service;

@Service
public class InterviewQuestionStructureServiceImpl implements InterviewQuestionStructureService {

    private final InterviewQuestionStructureRepository interviewQuestionStructureRepository;

    public InterviewQuestionStructureServiceImpl(InterviewQuestionStructureRepository interviewQuestionStructureRepository) {
        this.interviewQuestionStructureRepository = interviewQuestionStructureRepository;
    }

    @Override
    public InterviewQuestionStructureDTO getActualInterviewStructure() {
        InterviewQuestionStructureEntity questionStructure =  interviewQuestionStructureRepository.findByStatus(InterviewQuestionsStructureStatus.ACTIVE.getLabel())
                .orElseThrow(() -> new IllegalStateException("No se encontró una estructura de entrevista activa."));

        InterviewQuestionStructureDTO interviewQuestionStructureDTO = new InterviewQuestionStructureDTO();
        interviewQuestionStructureDTO.setQuestions(questionStructure.getQuestions());
        interviewQuestionStructureDTO.setId(questionStructure.getId());
        return interviewQuestionStructureDTO;
    }
}
