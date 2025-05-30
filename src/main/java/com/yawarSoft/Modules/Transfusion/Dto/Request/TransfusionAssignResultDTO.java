package com.yawarSoft.Modules.Transfusion.Dto.Request;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionAssignResultDTO {
    private Boolean type;
    private String observation;
}
