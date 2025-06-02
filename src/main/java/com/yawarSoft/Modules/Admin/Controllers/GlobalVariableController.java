package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Modules.Admin.Dto.GlobalVariableDTO;
import com.yawarSoft.Modules.Admin.Dto.GroupedVariablesDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.GlobalVariableService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/variables")
public class GlobalVariableController {

    private final GlobalVariableService globalVariableService;

    public GlobalVariableController(GlobalVariableService globalVariableService) {
        this.globalVariableService = globalVariableService;
    }

    @GetMapping("/grouped")
    @PreAuthorize("hasRole('ADMIN')")
    public GroupedVariablesDTO getGroupedGlobalVariables() {
        return globalVariableService.getAllGlobalVariablesGrouped();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GlobalVariableDTO getGroupedGlobalVariables(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        String value = body.get("value");
        return globalVariableService.editGlobalVariable(id, value);
    }
}
