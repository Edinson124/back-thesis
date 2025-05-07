package com.yawarSoft.Modules.Donation.Dto;

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
    private Boolean HIV;
    private Boolean HBsAg;
    private Boolean HBcAb;
    private Boolean HCV;
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
