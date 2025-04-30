package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Core.Entities.BloodBankTypeEntity;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blood-bank-type")
public class BloodBankTypeController {


    private final BloodBankTypeService bloodBankTypeService;

    public BloodBankTypeController(BloodBankTypeService bloodBankTypeService) {
        this.bloodBankTypeService = bloodBankTypeService;
    }

    @GetMapping()
    public List<BloodBankTypeEntity> getBloodBankTypesSelect() {
        return bloodBankTypeService.getBloodBankTypesActive();
    }
}
