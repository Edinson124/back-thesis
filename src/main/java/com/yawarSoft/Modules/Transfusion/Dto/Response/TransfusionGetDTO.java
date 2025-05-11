package com.yawarSoft.Modules.Transfusion.Dto.Response;

import com.yawarSoft.Modules.Transfusion.Dto.TransfusionAssignmentDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionRequestDetailDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionResultDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionViewDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionGetDTO {

    private Boolean canViewTransfusion ;
    private TransfusionViewDTO transfusion;
    private List<TransfusionRequestDetailDTO> request;
    private List<TransfusionAssignmentDTO> assignments;
    private TransfusionResultDTO result;
}
