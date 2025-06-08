package com.yawarSoft.Modules.Transfusion.Dto.Response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExistTransfusionDTO {

    private Boolean transfusionActualExists;
    private Boolean canViewTransfusion ;
    private Long transfusionId;
    private Long transfusionResultId;
    private Boolean isResultRegistrationAllowed;
}
