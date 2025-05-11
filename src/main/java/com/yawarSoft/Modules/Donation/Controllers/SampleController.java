package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Donation.Dto.Request.SampleTestRequest;
import com.yawarSoft.Modules.Donation.Dto.SampleDTO;
import com.yawarSoft.Modules.Donation.Services.Interfaces.SampleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sample")
public class SampleController {

    private final SampleService sampleService;


    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @PostMapping("/{idDonation}")
    public ResponseEntity<ApiResponse> createSample(@PathVariable Long idDonation, @RequestBody SampleTestRequest request) {
        Long id = sampleService.createSample(idDonation, request.test());
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Muestra registrada exitosamente", payload));
    }

    @GetMapping("/get/{idDonation}")
    public List<SampleDTO> getSamples(@PathVariable Long idDonation) {
        return sampleService.getSamples(idDonation);
    }
}
