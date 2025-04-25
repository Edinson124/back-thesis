package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleDTO> getRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{bloodBankTypeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleDTO> getRolesByBloodBankTypeId(@PathVariable Integer bloodBankTypeId) {
        return roleService.getRolesByBloodBankTypeId(bloodBankTypeId);
    }
}
