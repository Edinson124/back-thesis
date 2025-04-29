package com.yawarSoft.Modules.Donation.Controllers;


import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Donation.Dto.DonationResponseDTO;
import com.yawarSoft.Modules.Donation.Dto.DonationUpdateDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/donation")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/paginated/{idDonor}")
    public Page<DonationByDonorDTO> getDonationsByDonor(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @RequestParam() Long donorId) {
        return donationService.getDonationsByDonor(donorId, page, size);
    }

    @PostMapping
    public  ResponseEntity<ApiResponse> createDonation(@RequestBody DonationCreateRequest donationCreateRequest) {
        Long id = donationService.createDonation(donationCreateRequest);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Donación creada exitosamente", payload));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDonation(@PathVariable("id") Long id, @RequestBody DonationUpdateDTO donationUpdateDTO){
        DonationResponseDTO updatedDonation  = donationService.updateDonation(id, donationUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Donación actualizada exitosamente", updatedDonation));
    }
}
