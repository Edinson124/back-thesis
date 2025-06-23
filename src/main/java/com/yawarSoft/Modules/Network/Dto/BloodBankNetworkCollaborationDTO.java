package com.yawarSoft.Modules.Network.Dto;

import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankNetworkCollaborationDTO {

    private Integer id;
    private String name;
    private String coordinatorName;
    private String address;
    private String ubication;
    private String status;
    private String type;
    private Boolean isInternal;
    private Boolean canConnect;
}