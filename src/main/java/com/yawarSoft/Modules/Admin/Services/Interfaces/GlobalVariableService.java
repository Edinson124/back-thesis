package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Modules.Admin.Dto.GlobalVariableDTO;
import com.yawarSoft.Modules.Admin.Dto.GroupedVariablesDTO;

public interface GlobalVariableService {
    GroupedVariablesDTO getAllGlobalVariablesGrouped();
    GlobalVariableDTO editGlobalVariable(Integer id, String value);
}
