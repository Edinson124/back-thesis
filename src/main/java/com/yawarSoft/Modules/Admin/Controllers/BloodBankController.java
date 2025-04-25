package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankService;
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
    public List<BloodBankSelectOptionDTO> getBloodBankSelect() {
        return bloodBankService.getBloodBankSelector();
    }
}
