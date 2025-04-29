package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Modules.Admin.Dto.PermissionDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleListDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;


    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionDTO> getPermissions() {
        return permissionService.getAllPermissions();
    }
}
