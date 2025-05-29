package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Dto.Request.UserDocumentCheckRequest;
import com.yawarSoft.Modules.Admin.Dto.UserDTO;
import com.yawarSoft.Modules.Admin.Dto.UserListDTO;
import com.yawarSoft.Modules.Admin.Dto.UserSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import com.yawarSoft.Modules.Admin.Services.Interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserListDTO> getUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) Integer role,
                                      @RequestParam(required = false) String status) {
        return userService.getUsersPaginated(page, size, search, role, status);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PatchMapping("status/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserListDTO changeStatus(@PathVariable Integer userId) {
        return userService.changeStatus(userId);
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserStatus> getUserStatus() {
        return Arrays.asList(UserStatus.values());
    }

    @PostMapping("/exists")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> checkIfUserExists(@RequestBody UserDocumentCheckRequest request) {
        Boolean exists = userService.existsByDocument(request.userId(), request.documentNumber());
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/medic/{idBloodBank}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserSelectOptionDTO> getMedicUsersByBloodBank(@PathVariable Integer idBloodBank) {
        return userService.getMedicUsersByBloodBank(idBloodBank);
    }

    @GetMapping("/medicRequest")
    public List<UserSelectOptionDTO> getMedicRequestByBloodBank() {
        return userService.getMedicRequestUsers();
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