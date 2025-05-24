package com.yawarSoft.Modules.Admin.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Admin.Dto.NetworkDTO;
import com.yawarSoft.Modules.Admin.Dto.Request.BBNetworkRequestDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BBNetworkService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bb-network")
public class NetworkController {

    private final BBNetworkService bbNetworkService;

    public NetworkController(BBNetworkService bbNetworkService) {
        this.bbNetworkService = bbNetworkService;
    }

    @GetMapping
    public ResponseEntity<Page<NetworkDTO>> searchNetworks(
            @RequestParam(required = false) Integer idBloodBank,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<NetworkDTO> networks = bbNetworkService.searchByNameWithActualBloodBank(idBloodBank,name, page, size);
        return ResponseEntity.ok(networks);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NetworkDTO> getNetworkById(@PathVariable Integer id) {
        NetworkDTO networkDto = bbNetworkService.getById(id);
        return ResponseEntity.ok(networkDto);
    }

    @PostMapping("/associate/{networkId}/{bloodBankId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> associateBloodBank(@PathVariable Integer networkId, @PathVariable Integer bloodBankId) {
        bbNetworkService.associateBloodBank(networkId, bloodBankId);
        ApiResponse response = new ApiResponse(
                HttpStatus.OK,
                "Banco de sangre asociado exitosamente a la red."
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/disassociate/{networkId}/{bloodBankId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> disassociateBloodBank(@PathVariable Integer networkId, @PathVariable Integer bloodBankId) {
        bbNetworkService.disassociateBloodBank(networkId, bloodBankId);
        ApiResponse response = new ApiResponse(
                HttpStatus.OK,
                "Banco de sangre desasociado exitosamente de la red."
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createNetworkBB(@RequestBody BBNetworkRequestDTO bbNetworkRequestDTO) {
        Integer id = bbNetworkService.createNetworkBB(bbNetworkRequestDTO);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Red creada exitosamente", payload));
    }

    @PutMapping("{networkId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateNetworkBB(@PathVariable Integer networkId ,@RequestBody BBNetworkRequestDTO bbNetworkRequestDTO) {
        Integer id = bbNetworkService.updateNetworkBB(networkId,bbNetworkRequestDTO);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK, "Red editada exitosamente", payload));
    }
}
