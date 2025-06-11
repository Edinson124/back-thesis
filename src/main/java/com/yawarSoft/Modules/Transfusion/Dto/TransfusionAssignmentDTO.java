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

    private Long idUnit;
    private String stampPronahebas;
    private String bloodType;
    private String unitType;
    private String status;

    private String validateResult;
    private LocalDateTime validateResultDate;
    private Integer performedTestById;
    private String performedTestByName;
    private String observationTest;

    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;

}
