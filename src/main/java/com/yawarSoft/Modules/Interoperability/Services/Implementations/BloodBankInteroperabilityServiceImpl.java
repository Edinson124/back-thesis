package com.yawarSoft.Modules.Interoperability.Services.Implementations;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Modules.Admin.Enums.BloodBankStatus;
import com.yawarSoft.Modules.Interoperability.Enums.BloodBankTypeStatus;
import com.yawarSoft.Modules.Interoperability.Repositories.BloodBankInteroperabilityRepository;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.BloodBankInteroperabilityService;
import org.hl7.fhir.r4.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodBankInteroperabilityServiceImpl implements BloodBankInteroperabilityService {

    private final BloodBankInteroperabilityRepository bloodBankInteroperabilityRepository;

    public BloodBankInteroperabilityServiceImpl(BloodBankInteroperabilityRepository bloodBankInteroperabilityRepository) {
        this.bloodBankInteroperabilityRepository = bloodBankInteroperabilityRepository;
    }


    @Override
    public Bundle getPagedBloodBanks(int count, int page, String baseUrl) {
        Pageable pageable = PageRequest.of(page - 1, count);
        Page<BloodBankEntity> bloodBanks = bloodBankInteroperabilityRepository
                .findByStatusAndIsInternal(BloodBankTypeStatus.ACTIVE.name(), true, pageable);

        List<Organization> organizations = bloodBanks.getContent().stream()
                .map(this::mapToOrganization).toList();

        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.SEARCHSET);
        bundle.setTotal((int) bloodBanks.getTotalElements());

        for (Organization org : organizations) {
            bundle.addEntry()
                    .setFullUrl(baseUrl + "/Organization/" + org.getIdElement().getIdPart())
                    .setResource(org);
        }

        // Links
        int totalPages = bloodBanks.getTotalPages();

        bundle.addLink().setRelation("self").setUrl(buildUrl(baseUrl, count, page));
        if (page > 1) {
            bundle.addLink().setRelation("prev").setUrl(buildUrl(baseUrl, count, page - 1));
            bundle.addLink().setRelation("first").setUrl(buildUrl(baseUrl, count, 1));
        }
        if (page < totalPages) {
            bundle.addLink().setRelation("next").setUrl(buildUrl(baseUrl, count, page + 1));
            bundle.addLink().setRelation("last").setUrl(buildUrl(baseUrl, count, totalPages));
        }

        return bundle;
    }

    private String buildUrl(String baseUrl, int count, int page) {
        return baseUrl + "?_count=" + count + "&_page=" + page;
    }

    private Organization mapToOrganization(BloodBankEntity banco) {
        Organization org = new Organization();

        // ID del recurso
        org.setId(new IdType("Organization", banco.getId().toString()));

        // Nombre del banco de sangre
        org.setName(banco.getName());

        // Dirección
        Address address = new Address();
        address.setText(banco.getAddress());
        address.setDistrict(banco.getDistrict());
        address.setCity(banco.getProvince());
        address.setState(banco.getRegion());
        address.setCountry("PE"); // Perú, código ISO
        org.addAddress(address);

        // Tipo de organización (si deseas incluirlo)
        if (banco.getBloodBankType() != null) {
            CodeableConcept type = new CodeableConcept();
            type.setText(banco.getBloodBankType().getName());
            org.addType(type);
        }

        // Activo
        org.setActive(BloodBankTypeStatus.ACTIVE.name().equalsIgnoreCase(banco.getStatus()));

        return org;
    }


}
