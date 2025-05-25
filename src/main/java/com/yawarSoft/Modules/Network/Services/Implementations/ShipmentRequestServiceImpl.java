package com.yawarSoft.Modules.Network.Services.Implementations;

import com.yawarSoft.Core.Entities.ShipmentRequestEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import com.yawarSoft.Modules.Network.Mappers.ShipmentRequestMapper;
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
import java.util.List;

@Service
public class ShipmentRequestServiceImpl implements ShipmentRequestService {
    private final ShipmentRequestRepository shipmentRequestRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final ShipmentRequestMapper shipmentRequestMapper;

    public ShipmentRequestServiceImpl(ShipmentRequestRepository shipmentRequestRepository, AuthenticatedUserService authenticatedUserService, ShipmentRequestMapper shipmentRequestMapper) {
        this.shipmentRequestRepository = shipmentRequestRepository;
        this.authenticatedUserService = authenticatedUserService;
        this.shipmentRequestMapper = shipmentRequestMapper;
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
}
