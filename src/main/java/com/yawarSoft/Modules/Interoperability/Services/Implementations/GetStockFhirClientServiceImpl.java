package com.yawarSoft.Modules.Interoperability.Services.Implementations;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BearerTokenAuthInterceptor;
import ca.uhn.fhir.rest.gclient.IQuery;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yawarSoft.Core.Entities.ExternalEndpointEntity;
import com.yawarSoft.Core.Entities.ExternalSystemEntity;
import com.yawarSoft.Modules.Interoperability.Dtos.StockResponseDTO;
import com.yawarSoft.Modules.Interoperability.Repositories.ExternalEndpointRepository;
import com.yawarSoft.Modules.Interoperability.Repositories.ExternalSystemRepository;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.GetStockFhirClientService;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.LoginExternalSystemService;
import com.yawarSoft.Modules.Interoperability.Utils.ObservationSearchParameterHandler;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetStockFhirClientServiceImpl implements GetStockFhirClientService {

    private final FhirContext fhirContext;
    private final ExternalSystemRepository externalSystemRepository;
    private final ExternalEndpointRepository externalEndpointRepository;
    private final LoginExternalSystemService loginExternalSystemService;
    private final ObservationSearchParameterHandler observationSearchParameterHandler;

    public GetStockFhirClientServiceImpl(FhirContext fhirContext, ExternalSystemRepository externalSystemRepository, ExternalEndpointRepository externalEndpointRepository, LoginExternalSystemService loginExternalSystemService, ObservationSearchParameterHandler observationSearchParameterHandler) {
        this.fhirContext = fhirContext;
        this.externalSystemRepository = externalSystemRepository;
        this.externalEndpointRepository = externalEndpointRepository;
        this.loginExternalSystemService = loginExternalSystemService;
        this.observationSearchParameterHandler = observationSearchParameterHandler;
    }


    @Override
    public List<StockResponseDTO> getObservationsFromExternalSystem(Integer idBloodBank) {
        // Obtenemos el sistema externo asociado al banco de sangre
        ExternalSystemEntity externalSystem = externalSystemRepository.findByBloodBank_Id(idBloodBank)
                .orElseThrow(() -> new IllegalArgumentException("No existe un sistema externo registrado para el banco de sangre con id: " + idBloodBank));

        // Obtenemos el endpoint de Observation
        ExternalEndpointEntity observationEndpoint = externalEndpointRepository.findByExternalSystem_IdAndResourceNameAndInteractionType(
                externalSystem.getId(),
                "Observation",
                "search"
        ).orElseThrow(() -> new IllegalArgumentException("No existe un endpoint de búsqueda para Observation en este sistema."));

        // Autenticamos para obtener el token
        String token = loginExternalSystemService.obtainToken(externalSystem);

        // Preparamos el cliente FHIR
        IGenericClient client = fhirContext.newRestfulGenericClient(observationEndpoint.getPathBase());
        client.registerInterceptor(new BearerTokenAuthInterceptor(token));

        // Leemos los parámetros desde el template
        Map<String, String> parametersMap = new HashMap<>();
        if (observationEndpoint.getParametersTemplate() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> templateParams = mapper.readValue(observationEndpoint.getParametersTemplate(),
                        new TypeReference<Map<String, String>>() {});

                // Reemplazar los placeholders (Ej. ":id_blood_bank") con el valor real
                templateParams.forEach((key, value) -> {
                    if (value.equals(":id_blood_bank")) {
                        parametersMap.put(key, idBloodBank.toString());
                    } else {
                        parametersMap.put(key, value);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException("Error al procesar los parameters_template para Observation", e);
            }
        }
        // Ejecutamos la búsqueda
        IQuery<IBaseBundle> searchQuery = client.search().forResource(Observation.class);

        // Aplicar cada parámetro soportado
        searchQuery = observationSearchParameterHandler.applyParameters(searchQuery, parametersMap);

        // Ejecutamos y obtenemos el Bundle
        Bundle bundle = searchQuery.returnBundle(Bundle.class).execute();

        // Convertimos a DTO de Stock
        return toStockResponseList(bundle);
    }


    public List<StockResponseDTO> toStockResponseList(Bundle bundle) {
        return bundle.getEntry().stream()
                .map(Bundle.BundleEntryComponent::getResource)
                .filter(resource -> resource instanceof Observation)
                .map(resource -> (Observation) resource)
                .map(observation -> {
                    String unitType = observation.getCode().getText();
                    Long quantity = observation.getValueQuantity() != null && observation.getValueQuantity().getValue() != null
                            ? observation.getValueQuantity().getValue().longValue()
                            : 0L;

                    String bloodType = null;
                    String rhFactor = null;

                    for (Observation.ObservationComponentComponent component : observation.getComponent()) {
                        if ("Grupo sanguíneo".equals(component.getCode().getText())) {
                            // "Grupo sanguíneo O" -> Queremos "O"
                            String text = component.getValueCodeableConcept().getText();
                            if (text != null && text.startsWith("Grupo sanguíneo ")) {
                                bloodType = text.replace("Grupo sanguíneo ", "");
                            } else {
                                bloodType = text;
                            }
                        } else if ("Factor Rh".equals(component.getCode().getText())) {
                            // "Rh positivo" -> Queremos "POS"; "Rh negativo" -> "NEG"
                            String display = component.getValueCodeableConcept().getCodingFirstRep().getDisplay();
                            if ("Rh positivo".equals(display)) {
                                rhFactor = "POSITIVO";
                            } else if ("Rh negativo".equals(display)) {
                                rhFactor = "NEGATIVO";
                            } else {
                                rhFactor = display;
                            }
                        }
                    }

                    return StockResponseDTO.builder()
                            .bloodType(bloodType)
                            .rhFactor(rhFactor)
                            .unitType(unitType)
                            .quantity(quantity)
                            .build();
                })
                .toList();
    }

}

