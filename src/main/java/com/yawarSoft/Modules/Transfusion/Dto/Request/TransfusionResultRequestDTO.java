package com.yawarSoft.Modules.Transfusion.Dto.Request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionResultRequestDTO {

    private LocalDateTime transfusionDate;
    private String transfusionDoctorName;
    private String transfusionDoctorLicenseNumber;
    private Boolean hasReaction;
    private String reactionAdverse;
    private String observations;
}
