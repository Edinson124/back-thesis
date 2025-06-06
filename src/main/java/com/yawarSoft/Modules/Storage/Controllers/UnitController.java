package com.yawarSoft.Modules.Storage.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitExtractionDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Dto.UnitDTO;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/units")
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

    @PutMapping("/quarantined/unitSuitable/{idUnit}")
    public ResponseEntity<ApiResponse> unitSuitable(@PathVariable Long idUnit) {
        Long id = unitService.unitSuitable(idUnit);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK, "Cambio de unidad ha estado apta", payload));
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

    @GetMapping("/transformation/get/{idUnit}")
    public List<UnitExtractionDTO> getUnitsTransformationByUnit(@PathVariable Long idUnit) {
        return unitService.getUnitsTransformationByUnit(idUnit);
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
                                           @RequestParam(required = false) String status,
                                           @RequestParam(required = false) Long idTransfusion,
                                           @RequestParam(required = false) Boolean onlySuitable) {
        return unitService.getUnitsStock(page, size, startEntryDate, endEntryDate,
                startExpirationDate, endExpirationDate, bloodType, type, status, idTransfusion,onlySuitable);
    }

    @GetMapping("/{id}")
    public UnitDTO getUnitById(@PathVariable Long id) {
        return unitService.getUnitById(id);
    }

    @GetMapping("/get/{idDonation}")
    public List<UnitExtractionDTO> getUnitsByDonation(@PathVariable Long idDonation) {
        return unitService.getUnitsByDonation(idDonation);
    }

    @PostMapping("/save/{idDonation}")
    public UnitExtractionDTO saveUnitDonation(@PathVariable Long idDonation, @RequestBody UnitExtractionDTO unit) {
        return unitService.saveUnitDonation(idDonation, unit);
    }

    @PutMapping("/edit/{idUnit}")
    public UnitExtractionDTO editUnit(@PathVariable Long idUnit, @RequestBody UnitExtractionDTO unit) {
        return unitService.editUnit(idUnit, unit);
    }

    @PostMapping("/transformation/save/{idUnit}")
    public UnitExtractionDTO saveUnitTransformation(@PathVariable Long idUnit, @RequestBody UnitExtractionDTO unit) {
        return unitService.saveUnitTransformation(idUnit, unit);
    }

}
