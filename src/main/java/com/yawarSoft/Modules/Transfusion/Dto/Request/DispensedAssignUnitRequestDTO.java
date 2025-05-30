package com.yawarSoft.Modules.Transfusion.Dto.Request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DispensedAssignUnitRequestDTO {

    private String receivedByDocument;
    private String receivedByName;
}
