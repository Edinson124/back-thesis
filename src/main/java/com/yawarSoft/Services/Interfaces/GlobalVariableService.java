package com.yawarSoft.Services.Interfaces;

import com.yawarSoft.Dto.GlobalVariableDTO;
import com.yawarSoft.Dto.GroupedVariablesDTO;

public interface GlobalVariableService {
    GroupedVariablesDTO getAllGlobalVariablesGrouped();
    GlobalVariableDTO editGlobalVariable(Integer id, String value);
}
