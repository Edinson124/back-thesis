package com.yawarSoft.Modules.Donation.Dto.Request;

import com.fasterxml.jackson.databind.JsonNode;

public record InterviewAnswerRequest (JsonNode answer,
                                      Long donationId) {
}