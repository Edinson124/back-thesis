package com.yawarSoft.Modules.Donation.Dto.Request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodExtractionRequest {

    private Long donationId;
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
}
