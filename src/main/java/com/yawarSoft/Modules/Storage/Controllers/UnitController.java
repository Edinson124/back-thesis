package com.yawarSoft.Modules.Storage.Controllers;

import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }
    @GetMapping("/quarantined")
    public Page<UnitListDTO> getUnitsQuarantined(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(required = false)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startEntryDate,
                                      @RequestParam(required = false)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endEntryDate,
                                      @RequestParam(required = false)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startExpirationDate,
                                      @RequestParam(required = false)
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endExpirationDate,
                                      @RequestParam(required = false) String bloodType,
                                      @RequestParam(required = false) String type) {
        return unitService.getUnitsQuarantined(page, size, startEntryDate, endEntryDate,
                startExpirationDate, endExpirationDate, bloodType, type);
    }
}
