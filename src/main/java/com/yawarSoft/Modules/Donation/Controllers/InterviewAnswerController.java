package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Core.Entities.InterviewAnswerEntity;
import com.yawarSoft.Modules.Donation.Dto.InterviewAnswerDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.InterviewAnswerRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.InterviewAnswerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-answer")
public class InterviewAnswerController {

    private final InterviewAnswerService interviewAnswerService;

    public InterviewAnswerController(InterviewAnswerService interviewAnswerService) {
        this.interviewAnswerService = interviewAnswerService;
    }

    @PostMapping
    public InterviewAnswerDTO createInterviewAnswer(@RequestBody InterviewAnswerRequest interviewAnswerRequest) {
        return interviewAnswerService.createInterviewAnswer(interviewAnswerRequest);
    }

//    @PutMapping("/{id}")
//    public PhysicalAssessmentDTO updateInterviewAnswer(@PathVariable("id") Long id, @RequestBody PhysicalAssessmentRequest physicalAssessmentRequest){
//        return interviewAnswerService.updatePhysicalAssessment(id, physicalAssessmentRequest);
//    }

    @GetMapping("/{id}")
    public InterviewAnswerEntity getInterviewAnswer(@PathVariable("id") Long id) {
        return interviewAnswerService.getInterviewAnswer(id);
    }
}
