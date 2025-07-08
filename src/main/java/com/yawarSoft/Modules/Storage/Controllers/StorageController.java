package com.yawarSoft.Modules.Storage.Controllers;

import com.yawarSoft.Modules.Storage.Dto.Reponse.BloodStorageDTO;
import com.yawarSoft.Modules.Storage.Service.Interfaces.BloodStorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blood-storage")
public class StorageController {

    private final BloodStorageService bloodStorageService;

    public StorageController(BloodStorageService bloodStorageService) {
        this.bloodStorageService = bloodStorageService;
    }

    @GetMapping()
    public BloodStorageDTO getBloodStorage() {
        return bloodStorageService.getBloodStorage();
    }

}
