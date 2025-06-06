package com.yawarSoft.Modules.Laboratory.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Laboratory.Dto.HematologicalTestDTO;
import com.yawarSoft.Modules.Laboratory.Dto.Request.HematologicalTestRequest;
import com.yawarSoft.Modules.Laboratory.Services.Interfaces.HematologicalTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hematological-test")
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

    @GetMapping("{donationId}")
    public ResponseEntity<HematologicalTestDTO> getHematologicalTest(@PathVariable Long donationId) {
        HematologicalTestDTO test = hematologicalTestService.getHematologicalTest(donationId);
        if (test == null) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.ok(test);
    }
}
