package com.yawarSoft.Modules.Interoperability.Services.Implementations;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BearerTokenAuthInterceptor;
import ca.uhn.fhir.rest.gclient.IQuery;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import ca.uhn.fhir.rest.gclient.StringClientParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yawarSoft.Core.Entities.ExternalEndpointEntity;
import com.yawarSoft.Core.Entities.ExternalSystemEntity;
import com.yawarSoft.Modules.Interoperability.Dtos.StockResponseDTO;
import com.yawarSoft.Modules.Interoperability.Repositories.ExternalEndpointRepository;
import com.yawarSoft.Modules.Interoperability.Repositories.ExternalSystemRepository;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.GetStockFhirClientService;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.LoginExternalSystemService;
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

    public GetStockFhirClientServiceImpl(FhirContext fhirContext, ExternalSystemRepository externalSystemRepository, ExternalEndpointRepository externalEndpointRepository, LoginExternalSystemService loginExternalSystemService) {
        this.fhirContext = fhirContext;
        this.externalSystemRepository = externalSystemRepository;
        this.externalEndpointRepository = externalEndpointRepository;
        this.loginExternalSystemService = loginExternalSystemService;
    }


    @Override
    public List<StockResponseDTO> getObservationsFromExternalSystem(Integer idBloodBank) {
        // 1️⃣ Obtenemos el sistema externo asociado al banco de sangre
        ExternalSystemEntity externalSystem = externalSystemRepository.findByBloodBank_Id(idBloodBank)
                .orElseThrow(() -> new IllegalArgumentException("No existe un sistema externo registrado para el banco de sangre con id: " + idBloodBank));

        // 2️⃣ Obtenemos el endpoint de Observation
        ExternalEndpointEntity observationEndpoint = externalEndpointRepository.findByExternalSystem_IdAndResourceNameAndInteractionType(
                externalSystem.getId(),
                "Observation",
                "search"
        ).orElseThrow(() -> new IllegalArgumentException("No existe un endpoint de búsqueda para Observation en este sistema."));

        // 3️⃣ Autenticamos para obtener el token
        String token = loginExternalSystemService.obtainToken(externalSystem);

        // 4️⃣ Preparamos el cliente FHIR
        IGenericClient client = fhirContext.newRestfulGenericClient(observationEndpoint.getPathBase());
        client.registerInterceptor(new BearerTokenAuthInterceptor(token));

        // 5️⃣ Leemos los parámetros desde el template
        Map<String, String> parametersMap = new HashMap<>();
        if (observationEndpoint.getParametersTemplate() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> templateParams = mapper.readValue(observationEndpoint.getParametersTemplate(), new TypeReference<Map<String, String>>() {});

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
        // 6️⃣ Ejecutamos la búsqueda
        IQuery<IBaseBundle> searchQuery = client.search().forResource(Observation.class);

        // 7️⃣ Aplicar cada parámetro soportado
        for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if ("performer".equals(key)) {
                searchQuery = searchQuery.where(new ReferenceClientParam("performer").hasId(value));
            } else if ("name".equals(key)) {
                searchQuery = searchQuery.where(new StringClientParam("name").matches().value(value));
            }
            // Aquí puedes continuar añadiendo otros criterios soportados
        }

        // 8️⃣ Ejecutamos y obtenemos el Bundle
        Bundle bundle = searchQuery.returnBundle(Bundle.class).execute();

        // 9️⃣ Convertimos a DTO de Stock
        return toStockResponseList(bundle);
    }


//    @Override
//    public List<StockResponseDTO> getObservationsFromExternalSystem(Integer idBloodBank) {
//
//        // 1️⃣ Obtenemos el sistema externo asociado al banco de sangre
//        ExternalSystemEntity externalSystem = externalSystemRepository.findByBloodBank_Id(idBloodBank)
//                .orElseThrow(() -> new IllegalArgumentException("No existe un sistema externo registrado para el banco de sangre con id: " + idBloodBank));
//
//        // 2️⃣ Obtenemos el endpoint de Observation
//        ExternalEndpointEntity observationEndpoint = externalEndpointRepository.findByExternalSystem_IdAndResourceNameAndInteractionType(
//                externalSystem.getId(),
//                "Observation",
//                "search"
//        ).orElseThrow(() -> new IllegalArgumentException("No existe un endpoint de búsqueda para Observation en este sistema."));
//
//        String urlBaseExterno = "http://localhost:8085"; // Por ejemplo
//        String token = login(externalSystem);
//
//        String urlBaseExternoFhir = "http://localhost:8085/fhir";
//
//        IGenericClient client = fhirContext.newRestfulGenericClient(urlBaseExternoFhir);
//        client.registerInterceptor(new BearerTokenAuthInterceptor(token));
//
//        Bundle bundle = client.search()
//                .forResource(Observation.class)
//                .returnBundle(Bundle.class)
//                .execute();
//
//        return toStockResponseList(bundle);
//    }

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

