package com.yawarSoft.Modules.Interoperability.Utils;

import ca.uhn.fhir.rest.gclient.IQuery;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import ca.uhn.fhir.rest.gclient.StringClientParam;
import com.yawarSoft.Modules.Interoperability.Utils.Interfaces.FhirSearchParameterHandler;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ObservationSearchParameterHandler implements FhirSearchParameterHandler {
    @Override
    public IQuery<IBaseBundle> applyParameters(IQuery<IBaseBundle> query, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if ("performer".equals(key)) {
                query = query.where(new ReferenceClientParam("performer").hasId(value));
            } else if ("name".equals(key)) {
                query = query.where(new StringClientParam("name").matches().value(value));
            }
            // Añadir otros criterios soportados para Observation
        }
        return query;
    }
}
