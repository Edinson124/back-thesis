package com.yawarSoft.Modules.Storage.Controllers;

import com.yawarSoft.Modules.Donation.Dto.SampleDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitExtractionDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Dto.UnitDTO;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/transformation")
    public Page<UnitListDTO> getUnitsTransformation(@RequestParam(defaultValue = "0") int page,
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
        return unitService.getUnitsTransformation(page, size, startEntryDate, endEntryDate,
                startExpirationDate, endExpirationDate, bloodType, type);
    }

    @GetMapping("/stock")
    public Page<UnitListDTO> getUnitsStock(@RequestParam(defaultValue = "0") int page,
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
                                                    @RequestParam(required = false) String type,
                                           @RequestParam(required = false) String status) {
        return unitService.getUnitsStock(page, size, startEntryDate, endEntryDate,
                startExpirationDate, endExpirationDate, bloodType, type, status);
    }

    @GetMapping("/{id}")
    public UnitDTO getUnitById(@PathVariable Long id) {
        return unitService.getUnitById(id);
    }

    @GetMapping("/get/{idDonation}")
    public List<UnitExtractionDTO> getUnitsByDonation(@PathVariable Long idDonation) {
        return unitService.getUnitsByDonation(idDonation);
    }


}
