package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Modules.Donation.Dto.DonorDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donors")
public class DonorController {

    private final DonorService donorService;

    private DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping
    public DonorDTO createDonor(@RequestBody DonorDTO donorDTO) {
        return donorService.createDonor(donorDTO);
    }

    @PostMapping("/find")
    public DonorGetDTO getDonor(@RequestBody GetDonorRequest infoDonorRequest) {
        return donorService.getDonor(infoDonorRequest);
    }
}