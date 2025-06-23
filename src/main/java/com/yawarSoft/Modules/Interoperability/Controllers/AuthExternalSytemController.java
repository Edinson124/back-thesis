package com.yawarSoft.Modules.Interoperability.Controllers;

import com.yawarSoft.Modules.Interoperability.Dtos.AuthExternalDTO;
import com.yawarSoft.Modules.Interoperability.Dtos.Request.AuthExternalSystemRequestDTO;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.AuthExternalSystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth-external/")
public class AuthExternalSytemController {

    private final AuthExternalSystemService authExternalSystemService;

    public AuthExternalSytemController(AuthExternalSystemService authExternalSystemService) {
        this.authExternalSystemService = authExternalSystemService;
    }


    @GetMapping("/{idBloodBank}")
    public ResponseEntity<AuthExternalDTO> getAuthExternalByIdBloodBank(@PathVariable Integer idBloodBank) {
        return authExternalSystemService.getAuthExternalByIdBloodBank(idBloodBank)
                .map(ResponseEntity::ok)            // Si existe, retorna 200 + DTO
                .orElseGet(() -> ResponseEntity.noContent().build()); // Si no existe, retorna 204 No Content
    }


    @PostMapping("/{idBloodBank}")
    public AuthExternalDTO saveAuthExternal(@PathVariable Integer idBloodBank, @RequestBody AuthExternalSystemRequestDTO authExternalRequestDTO) {
        return authExternalSystemService.saveAuthExternal(idBloodBank, authExternalRequestDTO);
    }
}
