package com.yawarSoft.Modules.Network.Services.Implementations;

import com.yawarSoft.Core.Entities.NetworkEntity;
import com.yawarSoft.Core.Entities.ShipmentRequestEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Admin.Enums.NetworkBBStatus;
import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import com.yawarSoft.Modules.Network.Mappers.NetworkCollaborationMapper;
import com.yawarSoft.Modules.Network.Mappers.ShipmentRequestMapper;
import com.yawarSoft.Modules.Network.Repositories.NetworkRepository;
import com.yawarSoft.Modules.Network.Repositories.ShipmentRequestRepository;
import com.yawarSoft.Modules.Network.Services.Interfaces.ShipmentRequestService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentRequestServiceImpl implements ShipmentRequestService {
    private final ShipmentRequestRepository shipmentRequestRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final NetworkRepository networkRepository;
    private final ShipmentRequestMapper shipmentRequestMapper;
    private final NetworkCollaborationMapper networkCollaborationMapper;

    public ShipmentRequestServiceImpl(ShipmentRequestRepository shipmentRequestRepository, AuthenticatedUserService authenticatedUserService, NetworkRepository networkRepository, ShipmentRequestMapper shipmentRequestMapper, NetworkCollaborationMapper networkCollaborationMapper) {
        this.shipmentRequestRepository = shipmentRequestRepository;
        this.authenticatedUserService = authenticatedUserService;
        this.networkRepository = networkRepository;
        this.shipmentRequestMapper = shipmentRequestMapper;
        this.networkCollaborationMapper = networkCollaborationMapper;
    }

    @Override
    public Page<ShipmentRequestTableDTO> getShipments(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate,
                                                                      String status, Long code, Integer idBloodBank) {

        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        Integer originBankId = userAuth.getBloodBank().getId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        Specification<ShipmentRequestEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // Filtro por banco de sangre origen (del usuario autenticado)
            predicates.add(cb.equal(root.get("originBank").get("id"), originBankId));
            // Rango de fechas en requestDate
            if (startEntryDate != null && endEntryDate != null) {
                predicates.add(cb.between(
                        root.get("requestDate"),
                        startEntryDate.atStartOfDay(),
                        endEntryDate.atTime(23, 59, 59)
                ));
            } else if (startEntryDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("requestDate"), startEntryDate.atStartOfDay()));
            } else if (endEntryDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("requestDate"), endEntryDate.atTime(23, 59, 59)));
            }

            // Estado
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), status.toLowerCase()));
            }

            // Filtro por ID exacto (code)
            if (code != null) {
                predicates.add(cb.equal(root.get("id"), code));
            }

            // Banco de sangre destino
            if (idBloodBank != null) {
                predicates.add(cb.equal(root.get("destinationBank").get("id"), idBloodBank));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<ShipmentRequestEntity> shipmentsPage = shipmentRequestRepository.findAll(spec, pageable);
        return shipmentsPage.map(shipmentRequestMapper::toDto);
    }

    @Override
    public Page<ShipmentRequestTableDTO> getMyShipments(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, String status, Long code, Integer idBloodBank) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        Integer originBankId = userAuth.getBloodBank().getId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        Specification<ShipmentRequestEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // Filtro por banco de sangre destino (del usuario autenticado)
            predicates.add(cb.equal(root.get("destinationBank").get("id"), originBankId));
            // Rango de fechas en requestDate
            if (startEntryDate != null && endEntryDate != null) {
                predicates.add(cb.between(
                        root.get("requestDate"),
                        startEntryDate.atStartOfDay(),
                        endEntryDate.atTime(23, 59, 59)
                ));
            } else if (startEntryDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("requestDate"), startEntryDate.atStartOfDay()));
            } else if (endEntryDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("requestDate"), endEntryDate.atTime(23, 59, 59)));
            }

            // Estado
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), status.toLowerCase()));
            }

            // Filtro por ID exacto (code)
            if (code != null) {
                predicates.add(cb.equal(root.get("id"), code));
            }

            // Banco de sangre origen
            if (idBloodBank != null) {
                predicates.add(cb.equal(root.get("originBank").get("id"), idBloodBank));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<ShipmentRequestEntity> shipmentsPage = shipmentRequestRepository.findAll(spec, pageable);
        return shipmentsPage.map(shipmentRequestMapper::toDto);
    }

    @Override
    public List<NetworkCollaborationDTO> getNetworkToShipments() {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        Integer idBloodBank = userAuth.getBloodBank().getId();
        String activeStatus = NetworkBBStatus.ACTIVE.name();

        List<NetworkEntity> networks = networkRepository.findNetworksByBloodBankRelation(
                idBloodBank,
                activeStatus,
                activeStatus
        );

        return networks.stream()
                .peek(network -> {
                    // Solo relaciones activas
                    network.setBloodBankRelations(
                            network.getBloodBankRelations().stream()
                                    .filter(rel -> activeStatus.equals(rel.getStatus()))
                                    .collect(Collectors.toList())
                    );
                })
                .map(networkCollaborationMapper::toDto)
                .peek(dto -> dto.getBloodBankDetails().sort(Comparator.comparing(BloodBankNetworkCollaborationDTO::getId)))
                .collect(Collectors.toList());
    }

}
