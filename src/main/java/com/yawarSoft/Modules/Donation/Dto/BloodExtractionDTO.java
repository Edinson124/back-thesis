package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodExtractionDTO {

    private Long id;
    private LocalDateTime startDateTime;
    private Integer durationMinutes;
    private LocalDateTime endDateTime;
    private String arm;
    private Boolean adverseReactionOccurred;
    private String adverseReaction;
    private String otherReaction;
    private String status;
    private String observation;
    private BigDecimal processedBloodVolumeMl;
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;

}
