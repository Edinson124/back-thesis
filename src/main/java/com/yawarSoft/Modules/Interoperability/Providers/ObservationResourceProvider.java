package com.yawarSoft.Modules.Interoperability.Providers;

import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.UnitInteroperabilityService;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObservationResourceProvider implements IResourceProvider {

    private final UnitInteroperabilityService unitInteroperabilityService;

    public ObservationResourceProvider(UnitInteroperabilityService unitInteroperabilityService) {
        this.unitInteroperabilityService = unitInteroperabilityService;
    }

    @Override
    public Class<Observation> getResourceType() {
        return Observation.class;
    }

    @Search
    public List<Observation> getStockByBloodBank(@RequiredParam(name = "performer") StringParam bancoIdParam) {
        String input = bancoIdParam.getValue(); // Puede ser "2" o "Organization/2"
        String idSolo;

        if (input.matches("\\d+")) {
            // Es solo el ID numérico
            idSolo = input;
        } else if (input.matches("Organization/\\d+")) {
            // Es el formato Organization/2
            idSolo = input.split("/")[1];
        } else {
            throw new IllegalArgumentException("El parámetro performer debe ser un ID numérico o 'Organization/{id}'");
        }
        return unitInteroperabilityService.getStockByBloodBank(idSolo);
    }
}
