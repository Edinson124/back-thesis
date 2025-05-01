package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorDocumentCheckRequest;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/donors")
public class DonorController {

    private final DonorService donorService;

    private DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createDonor(@RequestBody DonorRequestDTO donorRequestDTO) {
        Long id = donorService.createDonor(donorRequestDTO);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Donante creado exitosamente", payload));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDonor(@PathVariable Long id, @RequestBody DonorRequestDTO donorRequestDTO) throws Exception {
        DonorGetDTO donorGetDTO = donorService.updateDonor(id, donorRequestDTO);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Donante actualizado exitosamente", donorGetDTO));
    }

    @PostMapping("/search")
    public DonorGetDTO getDonor(@RequestBody GetDonorRequest infoDonorRequest) {
        return donorService.getDonor(infoDonorRequest);
    }

    @PostMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> checkIfUserExists(@RequestBody DonorDocumentCheckRequest request) {
        Boolean exists = donorService.existsByDocument(request.donorId(), request.documentType(), request.documentNumber());
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}