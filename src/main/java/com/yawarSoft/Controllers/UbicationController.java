package com.yawarSoft.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ubication")
public class UbicationController {

    private final Map<String, Object> data;

    public UbicationController() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.data = objectMapper.readValue(new ClassPathResource("UbicationPeru.json").getInputStream(), Map.class);
    }

    @GetMapping
    public List<String> getDepartament() {
        return data.keySet().stream().toList();
    }

    // Obtener lista de provincias de un departamento
    @GetMapping("/{departament}")
    public List<String> getProvinces(@PathVariable String departament) {
        Map<String, Object> provincias = (Map<String, Object>) data.get(departament);
        return provincias != null ? provincias.keySet().stream().toList() : List.of();
    }

    // Obtener lista de distritos de una provincia
    @GetMapping("/{departament}/{provincie}")
    public List<String> getDistritos(@PathVariable String departament, @PathVariable String provincie) {
        Map<String, Object> provincias = (Map<String, Object>) data.get(departament);
        if (provincias == null) return List.of();

        Map<String, String> distritos = (Map<String, String>) provincias.get(provincie);
        return distritos != null ? distritos.keySet().stream().toList() : List.of();
    }
}
