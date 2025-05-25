package com.yawarSoft.Modules.Network.Dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankNetworkCollaborationDTO {

    private Integer id;
    private String name;
    private String coordinatorName;

    private String createdByFullName;
    private LocalDateTime createdAt;
    private String status;
}