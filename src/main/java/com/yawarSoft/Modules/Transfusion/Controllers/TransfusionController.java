package com.yawarSoft.Modules.Transfusion.Controllers;

import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TranfusionListDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionByPatientDTO;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/transfusion")
public class TransfusionController {


    private final TransfusionService transfusionService;

    public TransfusionController(TransfusionService transfusionService) {
        this.transfusionService = transfusionService;
    }

    @GetMapping("/paginated")
    public Page<TransfusionByPatientDTO> getDonationsByDonor(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size,
                                                             @RequestParam() String documentType,
                                                             @RequestParam() String documentNumber) {
        return transfusionService.getDonationsByDonor(documentType, documentNumber, page, size);
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
}
