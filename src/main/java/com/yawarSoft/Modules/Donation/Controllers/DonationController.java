package com.yawarSoft.Modules.Donation.Controllers;


import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Donation.Dto.DonationResponseDTO;
import com.yawarSoft.Modules.Donation.Dto.DonationUpdateDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DeferralDonationRequest;
import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequest;
import com.yawarSoft.Modules.Donation.Dto.Response.DateDonationDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.ExistDonationDTO;
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

    @GetMapping("/paginated")
    public Page<DonationByDonorDTO> getDonationsByDonor(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @RequestParam() String documentType,
                                                        @RequestParam() String documentNumber) {
        return donationService.getDonationsByDonor(documentType, documentNumber, page, size);
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

    @PostMapping("/actual")
    public  ResponseEntity<?>  getActualDonation(@RequestBody DonorRequest donorRequest) {
        DonationResponseDTO responseDTO = donationService.getActualDonation(donorRequest.documentType(),donorRequest.documentNumber());
        if (responseDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/last/date")
    public ResponseEntity<?>  getDateDetaillDonation(@RequestBody DonorRequest donorRequest) {
        DateDonationDTO responseDTO = donationService.getDateDetailLastDonation(donorRequest.documentType(),donorRequest.documentNumber());
        if (responseDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/exists/{id}")
    public ExistDonationDTO checkIfUserExistsAndPermitById(@PathVariable Long id) {
        return donationService.existsByCode(id);
    }

    @PostMapping("/exists")
    public  ExistDonationDTO  checkIfUserExistsAndPermitByDonor(@RequestBody DonorRequest donorRequest) {
        return donationService.existsActualByDonor(donorRequest.documentType(),donorRequest.documentNumber());
    }

    @GetMapping("/{id}")
        public DonationGetDTO getDonation(@PathVariable("id") Long id){
        return donationService.getDonationById(id);
    }

    @PostMapping("/deferral/{idDonation}")
    public Long finishDonationWithDeferral(@PathVariable("idDonation") Long idDonation, @RequestBody DeferralDonationRequest deferralDonationRequest){
        return donationService.finishDonationWithDeferral(idDonation, deferralDonationRequest);
    }
}
