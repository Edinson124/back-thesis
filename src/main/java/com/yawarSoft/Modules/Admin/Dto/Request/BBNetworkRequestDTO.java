package com.yawarSoft.Modules.Admin.Dto.Request;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BBNetworkRequestDTO {
    private Integer id;
    private String name;
    private String description;
    private Set<Integer> idBloodBanks;
}
