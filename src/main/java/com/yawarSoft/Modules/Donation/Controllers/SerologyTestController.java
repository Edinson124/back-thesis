package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Donation.Dto.Request.SerologyTestRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.SerologyTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/serology-test")
public class SerologyTestController {

    private final SerologyTestService serologyTestService;

    public SerologyTestController(SerologyTestService serologyTestService) {
        this.serologyTestService = serologyTestService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createSerologyTest(@RequestBody SerologyTestRequest serologyTestRequest) {
        Long id = serologyTestService.createSerologyTest(serologyTestRequest);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Prueba serológica registrada exitosamente", payload));
    }
}
