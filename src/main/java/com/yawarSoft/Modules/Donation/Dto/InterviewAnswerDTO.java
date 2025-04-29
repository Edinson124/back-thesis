package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewAnswerDTO {

    private Long id;
    private String answer;
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
}
