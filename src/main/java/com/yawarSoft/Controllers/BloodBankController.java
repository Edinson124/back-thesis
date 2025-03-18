package com.yawarSoft.Controllers;

import com.yawarSoft.Dto.BloodBankProjection;
import com.yawarSoft.Services.Interfaces.BloodBankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blood-bank")
public class BloodBankController {
    private final BloodBankService bloodBankService;

    public BloodBankController(BloodBankService bloodBankService) {
        this.bloodBankService = bloodBankService;
    }

    @GetMapping("/select")
    public List<BloodBankProjection> getBloodBankSelect() {
        return bloodBankService.getBloodBankSelector();
    }
}
