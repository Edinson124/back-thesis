package com.yawarSoft.Modules.Donation.Dto.Request;

public record InterviewAnswerRequest (Integer interviewStructureId,
                                      String answer,
                                      Long donationId) {
}