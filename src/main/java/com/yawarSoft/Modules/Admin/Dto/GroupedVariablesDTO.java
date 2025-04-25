package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupedVariablesDTO {
    private Map<String, List<GlobalVariableDTO>> groupedVariables;
}
