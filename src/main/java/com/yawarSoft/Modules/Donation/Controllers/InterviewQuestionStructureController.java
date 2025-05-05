package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Core.Entities.InterviewAnswerEntity;
import com.yawarSoft.Modules.Donation.Dto.Response.InterviewQuestionStructureDTO;
import com.yawarSoft.Modules.Donation.Services.Interfaces.InterviewQuestionStructureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interview-structure")
public class InterviewQuestionStructureController {

    private final InterviewQuestionStructureService interviewQuestionStructureService;

    public InterviewQuestionStructureController(InterviewQuestionStructureService interviewQuestionStructureService) {
        this.interviewQuestionStructureService = interviewQuestionStructureService;
    }

    @GetMapping
    public InterviewQuestionStructureDTO getInterviewQuestionStructure() {
        return interviewQuestionStructureService.getActualInterviewStructure();
    }
}
