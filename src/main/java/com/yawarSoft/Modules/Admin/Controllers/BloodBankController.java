package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Admin.Dto.BloodBankListDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankDTO;
import com.yawarSoft.Modules.Admin.Dto.Reponse.BloodBankOptionsAddNetworkDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/blood-bank")
public class BloodBankController {
    private final BloodBankService bloodBankService;

    public BloodBankController(BloodBankService bloodBankService) {
        this.bloodBankService = bloodBankService;
    }

    @GetMapping("/select/internal")
    public List<BloodBankSelectOptionDTO> getBloodBankSelectInternal() {
        return bloodBankService.getBloodBankSelectorInternal();
    }

    @GetMapping("/select")
    public List<BloodBankSelectOptionDTO> getBloodBankSelect() {
        return bloodBankService.getBloodBankSelectorAll();
    }


    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<BloodBankListDTO> getBloodBank(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "6") int size,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String region,
                                           @RequestParam(required = false) String province,
                                           @RequestParam(required = false) String district,
                                           @RequestParam(required = false) Boolean isInternal) {
        return bloodBankService.getBloodBankPaginated(page, size, name, region, province,district, isInternal);
    }

    @GetMapping("/paginated/external")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<BloodBankListDTO> getBloodBankExternal(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "6") int size,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String region,
                                               @RequestParam(required = false) String province,
                                               @RequestParam(required = false) String district) {
        return bloodBankService.getBloodBankPaginated(page, size, name, region, province,district, false);
    }


    @GetMapping("/optionsNetwork")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<BloodBankOptionsAddNetworkDTO> getBloodBankOptionsNetwork(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "6") int size,
                                                                          @RequestParam(required = false) String name) {
        return bloodBankService.getBloodBankOptionsNetwork(page, size, name);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BloodBankDTO getBloodBankById(@PathVariable Integer id) {
        return bloodBankService.getBloodBankById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BloodBankDTO updateBloodBank(@PathVariable Integer id, @RequestBody BloodBankDTO bloodBankDTO) {
        return bloodBankService.updateBloodBank(id, bloodBankDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BloodBankDTO createBloodBank(@RequestBody BloodBankDTO bloodBankDTO) {
        return bloodBankService.createBloodBank(bloodBankDTO);
    }

    @PatchMapping("status/{bloodBankId}")
    @PreAuthorize("hasRole('ADMIN')")
    public BloodBankDTO changeStatus(@PathVariable Integer bloodBankId) {
        return bloodBankService.changeStatus(bloodBankId);
    }

    @PostMapping("img-profile/{idBloodBank}")
    public ResponseEntity<ApiResponse> uploadProfileImage(@PathVariable Integer idBloodBank,
                                                          @RequestParam("image") MultipartFile file) {
        try {
            String message = bloodBankService.updateBloodBankProfileImage(idBloodBank, file);
            return ResponseEntity.ok().body(new ApiResponse(HttpStatus.OK,message));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error al subir la imagen: " + e.getMessage()));
        }
    }

    @DeleteMapping("img-profile/{idBloodBank}")
    public ResponseEntity<ApiResponse> deleteProfileImage(@PathVariable Integer idBloodBank) {
        return bloodBankService.deleteBloodBankProfileImage(idBloodBank);
    }
}
