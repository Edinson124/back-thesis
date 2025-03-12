package com.yawarSoft.Controllers;

import com.yawarSoft.Dto.RoleDTO;
import com.yawarSoft.Services.Interfaces.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
}
