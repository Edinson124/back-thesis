package com.yawarSoft.Controllers;

import com.yawarSoft.Dto.GroupedVariablesResponse;
import com.yawarSoft.Services.Interfaces.GlobalVariableService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/variables")
public class GlobalVariableController {

    private final GlobalVariableService globalVariableService;

    public GlobalVariableController(GlobalVariableService globalVariableService) {
        this.globalVariableService = globalVariableService;
    }

    @GetMapping("/grouped")
    @PreAuthorize("hasRole('ADMIN')")
    public GroupedVariablesResponse getGroupedGlobalVariables() {
        return globalVariableService.getAllGlobalVariablesGrouped();
    }
}
