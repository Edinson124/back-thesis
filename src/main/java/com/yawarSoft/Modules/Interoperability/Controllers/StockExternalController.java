package com.yawarSoft.Modules.Interoperability.Controllers;

import com.yawarSoft.Modules.Interoperability.Dtos.StockResponseDTO;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.GetStockFhirClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/interoperability/")
public class StockExternalController {

    @Autowired
    private GetStockFhirClientService fhirClientService;

    @GetMapping("/stock/{idBloodBank}")
    public List<StockResponseDTO> getExternalStock(@PathVariable Integer idBloodBank) {
        return fhirClientService.getObservationsFromExternalSystem(idBloodBank);
    }
}
