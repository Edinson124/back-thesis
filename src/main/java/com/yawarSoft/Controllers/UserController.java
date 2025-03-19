package com.yawarSoft.Controllers;

import com.yawarSoft.Controllers.Dto.UserRequest;
import com.yawarSoft.Dto.ApiResponse;
import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Enums.UserStatus;
import com.yawarSoft.Services.Interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    public UserEntity getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest.getUser(), userRequest.getRoleIds());
    }

    @PatchMapping("status/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO changeStatus(@PathVariable Integer userId) {
        return userService.changeStatus(userId);
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserStatus> getUserStatus() {
        return Arrays.asList(UserStatus.values());
    }

    @PostMapping("img-profile/{idUser}")
    public ResponseEntity<ApiResponse> uploadProfileImage(@PathVariable Integer idUser,
                                                     @RequestParam("image") MultipartFile file) {
        try {
            String message = userService.updateUserProfileImage(idUser, file);
            return ResponseEntity.ok().body(new ApiResponse(HttpStatus.OK,message));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error al subir la imagen: " + e.getMessage()));
        }
    }

    @DeleteMapping("img-profile/{idUser}")
    public ResponseEntity<ApiResponse> deleteProfileImage(@PathVariable Integer idUser) {
        return userService.deleteUserProfileImage(idUser);
    }
}