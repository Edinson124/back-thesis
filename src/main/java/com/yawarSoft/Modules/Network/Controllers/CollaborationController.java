package com.yawarSoft.Modules.Network.Controllers;

import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Response.OptionBloodBankNetworkDTO;
import com.yawarSoft.Modules.Network.Dto.Response.StockNetworkDTO;
import com.yawarSoft.Modules.Network.Services.Interfaces.CollaborationService;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/collaboration")
public class CollaborationController {

    private final CollaborationService collaborationService;


    public CollaborationController(CollaborationService collaborationService) {
        this.collaborationService = collaborationService;
    }

    @GetMapping("/network/{networkId}")
    public ResponseEntity<OptionBloodBankNetworkDTO> getBloodBanksOptions(@PathVariable("networkId") Integer networkId) {
         OptionBloodBankNetworkDTO options = collaborationService.getBBOptionsNetwork(networkId);
        return ResponseEntity.ok(options);
    }

    @GetMapping("/networks")
    public ResponseEntity<Page<NetworkCollaborationDTO>> searchNetworks(
            @RequestParam(required = false) Integer idBloodBank,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<NetworkCollaborationDTO> networks = collaborationService.searchNetworksByUserAndOptionalFilters(name,
                idBloodBank, page, size);
        return ResponseEntity.ok(networks);
    }

    @GetMapping("/stock")
    public StockNetworkDTO getUnitsStock(@RequestParam() Integer idBloodBank,
                                         @RequestParam() Integer idNetwork,
                                         @RequestParam(defaultValue = "0") int page,
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
        return collaborationService.getUnitsStock(idBloodBank, idNetwork, page, size, startEntryDate, endEntryDate,
                startExpirationDate, endExpirationDate, bloodType, type);
    }
}
