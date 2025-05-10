package com.yawarSoft.Modules.Transfusion.Controllers;

import com.yawarSoft.Modules.Transfusion.Dto.Response.ExistTransfusionDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionDetailDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TranfusionListDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionByPatientDTO;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionRequestService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/transfusion")
public class TransfusionRequestController {


    private final TransfusionRequestService transfusionService;

    public TransfusionRequestController(TransfusionRequestService transfusionService) {
        this.transfusionService = transfusionService;
    }

    @GetMapping("/paginated")
    public Page<TransfusionByPatientDTO> getTranfusionByPatient(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size,
                                                             @RequestParam() String documentType,
                                                             @RequestParam() String documentNumber) {
        return transfusionService.getTranfusionByPatient(documentType, documentNumber, page, size);
    }

    @GetMapping("/request")
    public Page<TranfusionListDTO> getTransfusion(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startEntryDate,
                                                  @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endEntryDate,
                                                  @RequestParam(required = false) Long code,
                                                  @RequestParam(required = false) String status) {
        return transfusionService.getTransfusions(page, size, startEntryDate, endEntryDate, code, status);
    }

    @GetMapping("/exists/{id}")
    public ExistTransfusionDTO checkIfUserExistsAndPermitById(@PathVariable Long id) {
        return transfusionService.existsByCode(id);
    }

    @GetMapping("/detail/{id}")
    public TransfusionDetailDTO getDetailTransfusion(@PathVariable Long id) {
        return transfusionService.getDetailTransfusion(id);
    }
}
