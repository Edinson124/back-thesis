package com.yawarSoft.Modules.Transfusion.Dto;

import lombok.*;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionResultDTO {
    private Long id;
    private LocalDateTime transfusionDate;
    private String transfusionDoctorName;
    private String transfusionDoctorLicenseNumber;
    private Boolean hasReaction;
    private String reactionAdverse;
    private String observations;

    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;
}
