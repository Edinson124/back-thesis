package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.InterviewAnswerDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.InterviewAnswerRequest;

public interface InterviewAnswerService {
    InterviewAnswerDTO createInterviewAnswer(InterviewAnswerRequest interviewAnswerRequest);
    InterviewAnswerDTO getInterviewAnswer(Long idDonation);
}
