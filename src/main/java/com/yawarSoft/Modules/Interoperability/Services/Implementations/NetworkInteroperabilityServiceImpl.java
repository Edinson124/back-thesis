package com.yawarSoft.Modules.Interoperability.Services.Implementations;

import com.yawarSoft.Core.Entities.ExternalSystemEntity;
import com.yawarSoft.Modules.Interoperability.Repositories.ExternalSystemRepository;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.NetworkInteroperabilityService;
import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Services.Interfaces.ShipmentRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NetworkInteroperabilityServiceImpl implements NetworkInteroperabilityService {

    private final ShipmentRequestService shipmentRequestService;
    private final ExternalSystemRepository externalSystemRepository;

    public NetworkInteroperabilityServiceImpl(ShipmentRequestService shipmentRequestService, ExternalSystemRepository externalSystemRepository) {
        this.shipmentRequestService = shipmentRequestService;
        this.externalSystemRepository = externalSystemRepository;
    }

    @Override
    public List<NetworkCollaborationDTO> getNetworkToInteroperability() {
        // Obtenemos todas las redes
        List<NetworkCollaborationDTO> networks = shipmentRequestService.getNetworkToShipments();

        // Identificamos todas las ids de bancos NO internos
        List<Integer> externalBloodBankIds = networks.stream()
                .flatMap(net -> net.getBloodBankDetails().stream())
                .filter(bdDetail -> !bdDetail.getIsInternal())
                .map(BloodBankNetworkCollaborationDTO::getId)
                .toList();

        // Si no hay bancos externos, retornar todas las redes pero con detalles vacíos
        if (externalBloodBankIds.isEmpty()) {
            return networks.stream()
                    .map(net -> {
                        NetworkCollaborationDTO copy = NetworkCollaborationDTO.builder()
                                .id(net.getId())
                                .name(net.getName())
                                .description(net.getDescription())
                                .bloodBankDetails(List.of()) // Vacía
                                .build();
                        return copy;
                    })
                    .toList();
        }

        // Consultamos todas las external systems ACTIVAS para esos ids
        List<ExternalSystemEntity> externalSystems = externalSystemRepository.findByBloodBankIdInAndIsActiveTrue(externalBloodBankIds);
        Set<Integer> externalSystemBloodBankIds = externalSystems.stream()
                .map(es -> es.getBloodBank().getId())
                .collect(Collectors.toSet());

        // 5️⃣ Filtramos para quedarse solo con detalles externos y marcar canQueryExternal
        return networks.stream()
                .map(net -> {
                    List<BloodBankNetworkCollaborationDTO> externalDetails = net.getBloodBankDetails().stream()
                            .filter(bdDetail -> !bdDetail.getIsInternal())
                            .peek(bdDetail -> bdDetail.setCanConnect(externalSystemBloodBankIds.contains(bdDetail.getId())))
                            .toList();

                    return NetworkCollaborationDTO.builder()
                            .id(net.getId())
                            .name(net.getName())
                            .description(net.getDescription())
                            .bloodBankDetails(externalDetails)
                            .build();
                })
                .toList();

    }

}
