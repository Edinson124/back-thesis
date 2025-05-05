package com.yawarSoft.Modules.Donation.Dto.Response;

import com.yawarSoft.Core.Entities.InterviewQuestionStructureEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewQuestionStructureDTO {
    private Long id;
    private String questions;
}
