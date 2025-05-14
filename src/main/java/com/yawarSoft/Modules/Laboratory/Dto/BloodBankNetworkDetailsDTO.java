package com.yawarSoft.Modules.Laboratory.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankNetworkDetailsDTO {

    private Integer id;
    private String name;
    private String coordinatorName;

    private String createdByFullName;
    private LocalDateTime createdAt;

    private String disassociatedByFullName;
    private LocalDateTime disassociatedAt;
    private String status;
}
