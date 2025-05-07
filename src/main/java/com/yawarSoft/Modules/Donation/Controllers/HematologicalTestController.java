package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Donation.Dto.Request.HematologicalTestRequest;
import com.yawarSoft.Modules.Donation.Dto.Request.SerologyTestRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.HematologicalTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hematological-test")
public class HematologicalTestController {

    private final HematologicalTestService hematologicalTestService;

    public HematologicalTestController(HematologicalTestService hematologicalTestService) {
        this.hematologicalTestService = hematologicalTestService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createHematologicalTest(@RequestBody HematologicalTestRequest hematologicalTestRequest) {
        Long id = hematologicalTestService.createHematologicalTest(hematologicalTestRequest);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Prueba heamtológica registrada exitosamente", payload));
    }
}
