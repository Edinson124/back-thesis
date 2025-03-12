package com.yawarSoft.Controllers;

import com.yawarSoft.Controllers.Dto.UserRequest;
import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Services.Interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDTO> getUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) String role,
                                  @RequestParam(required = false) String status) {
        return userService.getUsersPaginated(page, size, search, role, status);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest.getUser(), userRequest.getRoleIds());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deactivateUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return "Usuario con ID " + userId + " ha sido desactivado.";
    }
}