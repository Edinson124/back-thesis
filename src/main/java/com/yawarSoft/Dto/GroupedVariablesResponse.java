package com.yawarSoft.Dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupedVariablesResponse {
    private Map<String, List<GlobalVariableDTO>> groupedVariables;
}
