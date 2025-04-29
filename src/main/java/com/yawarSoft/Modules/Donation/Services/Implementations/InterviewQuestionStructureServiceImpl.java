package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Core.Entities.InterviewAnswerEntity;
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
    public InterviewAnswerEntity getActualInterviewStrcuture() {
        return interviewQuestionStructureRepository.findByStatus(InterviewQuestionsStructureStatus.ACTIVE.name())
                .orElseThrow(() -> new IllegalStateException("No se encontró una estructura de entrevista activa."));
    }
}
