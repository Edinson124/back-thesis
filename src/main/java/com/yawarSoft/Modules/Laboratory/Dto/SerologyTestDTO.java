package com.yawarSoft.Modules.Laboratory.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SerologyTestDTO {

    private Long id;
    private LocalDate testDate;
    private Boolean hiv;
    private Boolean hbsAg;
    private Boolean hbcAb;
    private Boolean hcv;
    private Boolean syphilis;
    private Boolean chagas;
    private Boolean htlvI_II;
    private String status;
    private String observation;
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;
}
