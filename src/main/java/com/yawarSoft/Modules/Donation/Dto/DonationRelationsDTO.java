package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationRelationsDTO {

    private Integer idBloodBank;
    private Long idPhysicalAssessment;
    private Long idSerologyTest;
    private Long idHematologicalTest;
    private Long idInterviewAnswer;
    private Long idBloodExtraction;

}
