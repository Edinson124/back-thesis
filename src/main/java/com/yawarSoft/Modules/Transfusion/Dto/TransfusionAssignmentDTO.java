package com.yawarSoft.Modules.Transfusion.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionAssignmentDTO {
    private Long id;
    private Long bloodUnitId;
    private String bloodUnitType;
    private String bloodUnitBloodType;
    private String status;

    private String crossmatchResult;
    private LocalDateTime crossmatchTestDate;
    private Integer performedTestById;
    private String performedTestByName;
    private String observationTest;

    private LocalDateTime dispensedDate;
    private String dispensedByDocument;
    private String dispensedByName;

    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;

}
