package com.yawarSoft.Modules.Interoperability.Services.Interfaces;

import org.hl7.fhir.r4.model.Observation;

import java.util.List;

public interface UnitInteroperabilityService {
    List<Observation> getStockByBloodBank(String bancoId);
}
