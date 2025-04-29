package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleListDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<RoleListDTO> getRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{bloodBankTypeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleListDTO> getRolesByBloodBankTypeId(@PathVariable Integer bloodBankTypeId) {
        return roleService.getRolesByBloodBankTypeId(bloodBankTypeId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDTO getBloodBankById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDTO createBloodBank(@RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDTO updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(id, roleDTO);
    }
}
