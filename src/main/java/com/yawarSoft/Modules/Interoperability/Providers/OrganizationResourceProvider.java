package com.yawarSoft.Modules.Interoperability.Providers;

import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.BloodBankInteroperabilityService;
import jakarta.servlet.http.HttpServletRequest;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;

@Component
public class OrganizationResourceProvider implements IResourceProvider {

    private final BloodBankInteroperabilityService bloodBankFHIRService;

    public OrganizationResourceProvider(BloodBankInteroperabilityService bloodBankFHIRService) {
        this.bloodBankFHIRService = bloodBankFHIRService;
    }

    @Override
    public Class<Organization> getResourceType() {
        return Organization.class;
    }

    @Search
    public Bundle searchActiveInternalBloodBanks(
            @OptionalParam(name = "_page") String pageStr,
            HttpServletRequest request
    ) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException | NullPointerException e) {
            // por defecto se queda en página 1
        }

        return bloodBankFHIRService.getPagedBloodBanks(10, page, request.getRequestURL().toString());
    }
}
