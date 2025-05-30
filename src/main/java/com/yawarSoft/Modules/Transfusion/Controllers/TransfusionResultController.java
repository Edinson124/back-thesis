package com.yawarSoft.Modules.Transfusion.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionResultRequestDTO;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transfusion-result")
public class TransfusionResultController {

    private final TransfusionResultService transfusionResultService;

    public TransfusionResultController(TransfusionResultService transfusionResultService) {
        this.transfusionResultService = transfusionResultService;
    }

    @PostMapping("/{idTransfusion}")
    public  ResponseEntity<ApiResponse> createTransfusionResult(@PathVariable Long idTransfusion, @RequestBody TransfusionResultRequestDTO request) {
        Long id = transfusionResultService.createTransfusionResult(idTransfusion, request);
        String message = "Transfusion creada exitosamente";
        if( id==0 ){
            message = "La transfusion ya tiene un resultado registrado";
        }
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, message, payload));
    }
}
