package com.yawarSoft.Modules.Interoperability.Controllers;

import com.yawarSoft.Modules.Interoperability.Services.Interfaces.NetworkInteroperabilityService;
import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/interoperability/")
public class NetworkInteroperabilityController {

    private final NetworkInteroperabilityService networkInteroperabilityService;

    public NetworkInteroperabilityController(NetworkInteroperabilityService networkInteroperabilityService) {
        this.networkInteroperabilityService = networkInteroperabilityService;
    }

    @GetMapping("/networks")
    public ResponseEntity<List<NetworkCollaborationDTO>> getNetworkToInteroperability() {
        List<NetworkCollaborationDTO> networks =  networkInteroperabilityService.getNetworkToInteroperability();
        return ResponseEntity.ok(networks);
    }
}
