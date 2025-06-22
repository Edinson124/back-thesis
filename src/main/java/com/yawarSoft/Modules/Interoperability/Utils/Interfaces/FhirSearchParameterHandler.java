package com.yawarSoft.Modules.Interoperability.Utils.Interfaces;

import ca.uhn.fhir.rest.gclient.IQuery;
import org.hl7.fhir.instance.model.api.IBaseBundle;

import java.util.Map;

public interface FhirSearchParameterHandler {
    IQuery<IBaseBundle> applyParameters(IQuery<IBaseBundle> query, Map<String, String> parameters);
}
