package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Modules.Admin.Dto.RoleDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleListDTO;
import com.yawarSoft.Modules.Admin.Dto.RoleSelectDTO;
import com.yawarSoft.Modules.Admin.Dto.UserListDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
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

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<RoleListDTO> getRoles(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) String status) {
        return roleService.getRolesPaginated(page, size, search, status);
    }

    @GetMapping("/select")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleSelectDTO> getRolesSelectActive() {
        return roleService.getRolesSelectActive();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDTO getRoleById(@PathVariable Integer id) {
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
