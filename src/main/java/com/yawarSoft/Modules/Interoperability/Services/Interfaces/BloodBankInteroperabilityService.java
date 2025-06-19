package com.yawarSoft.Modules.Interoperability.Services.Interfaces;

import org.hl7.fhir.r4.model.Bundle;

public interface BloodBankInteroperabilityService {
    Bundle getPagedBloodBanks(int count, int page, String baseUrl);
}
