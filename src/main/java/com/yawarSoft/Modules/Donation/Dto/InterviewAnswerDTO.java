package com.yawarSoft.Modules.Donation.Dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewAnswerDTO {

    private Long id;
    private JsonNode answer;
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
}
