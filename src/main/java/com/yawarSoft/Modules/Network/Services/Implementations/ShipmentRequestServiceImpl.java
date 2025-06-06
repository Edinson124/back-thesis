package com.yawarSoft.Modules.Network.Services.Implementations;

import com.yawarSoft.Core.Entities.*;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Admin.Enums.NetworkBBStatus;
import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Request.RequestDetailsUnitDTO;
import com.yawarSoft.Modules.Network.Dto.Request.ShipmentRequestDTO;
import com.yawarSoft.Modules.Network.Dto.Response.ShipmentDTO;
import com.yawarSoft.Modules.Network.Dto.Response.ShipmentWithAssignmentDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import com.yawarSoft.Modules.Network.Enums.ShipmentRequestStatus;
import com.yawarSoft.Modules.Network.Mappers.NetworkCollaborationMapper;
import com.yawarSoft.Modules.Network.Mappers.ShipmentRequestMapper;
import com.yawarSoft.Modules.Network.Mappers.ShipmentXUnitMapper;
import com.yawarSoft.Modules.Network.Repositories.NetworkRepository;
import com.yawarSoft.Modules.Network.Repositories.ShipmentRequestRepository;
import com.yawarSoft.Modules.Network.Services.Interfaces.ShipmentRequestService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShipmentRequestServiceImpl implements ShipmentRequestService {
    private final ShipmentRequestRepository shipmentRequestRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final NetworkRepository networkRepository;
    private final ShipmentRequestMapper shipmentRequestMapper;
    private final NetworkCollaborationMapper networkCollaborationMapper;
    private final ShipmentXUnitMapper shipmentXUnitMapper;

    public ShipmentRequestServiceImpl(ShipmentRequestRepository shipmentRequestRepository, AuthenticatedUserService authenticatedUserService, NetworkRepository networkRepository, ShipmentRequestMapper shipmentRequestMapper, NetworkCollaborationMapper networkCollaborationMapper, ShipmentXUnitMapper shipmentXUnitMapper) {
        this.shipmentRequestRepository = shipmentRequestRepository;
        this.authenticatedUserService = authenticatedUserService;
        this.networkRepository = networkRepository;
        this.shipmentRequestMapper = shipmentRequestMapper;
        this.networkCollaborationMapper = networkCollaborationMapper;
        this.shipmentXUnitMapper = shipmentXUnitMapper;
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

    @Override
    public Integer createShipment(ShipmentRequestDTO shipmentRequestDTO) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        BloodBankEntity bloodBank = userAuth.getBloodBank();
        BloodBankEntity bloodBankOrigin = new BloodBankEntity();
        bloodBankOrigin.setId(shipmentRequestDTO.getIdBloodBank());

        ShipmentRequestEntity shipmentRequest = new ShipmentRequestEntity();
        shipmentRequest.setCreatedAt(LocalDateTime.now());
        shipmentRequest.setCreatedBy(userAuth);
        shipmentRequest.setDetails(shipmentRequestDTO.getDetails());
        shipmentRequest.setReason(shipmentRequestDTO.getReason());
        shipmentRequest.setDestinationBank(bloodBank);
        shipmentRequest.setOriginBank(bloodBankOrigin);
        shipmentRequest.setRequestDate(LocalDateTime.now());
        shipmentRequest.setStatus(ShipmentRequestStatus.PENDING.getLabel());

        // Convertir unidades del DTO en entidades
        List<ShipmentRequestDetailEntity> detailEntities = new ArrayList<>();
        for (RequestDetailsUnitDTO dto : shipmentRequestDTO.getUnits()) {
            ShipmentRequestDetailEntity detail = new ShipmentRequestDetailEntity();
            detail.setUnitType(dto.getUnitType());
            detail.setRequestedQuantity(dto.getRequestedQuantity());
            detail.setBloodType(dto.getBloodGroup());
            detail.setRhFactor(dto.getRhFactor());
            detail.setCreatedBy(userAuth);
            detail.setCreatedAt(LocalDateTime.now());
            detail.setShipmentRequest(shipmentRequest);

            detailEntities.add(detail);
        }
        shipmentRequest.setUnitsRequest(detailEntities);
        ShipmentRequestEntity shipmentRequestSaved = shipmentRequestRepository.save(shipmentRequest);

        return shipmentRequestSaved.getId();
    }

    @Transactional
    @Override
    public ShipmentDTO getShipment(Integer idShipment) {
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        ShipmentRequestEntity shipmentRequest = shipmentRequestRepository
                .findById(idShipment).orElseThrow(()->new IllegalArgumentException("Solicitud de transferencia no encontrada"));
        Integer bloodBankUser = userAuth.getBloodBank().getId();
        Integer bloodBankDestination = shipmentRequest.getDestinationBank().getId();
        if(!Objects.equals(bloodBankUser, bloodBankDestination)){
            shipmentDTO.setCanViewRequest(false);
            return shipmentDTO;
        }

        shipmentDTO.setCanViewRequest(true);
        shipmentDTO.setBloodBankOrigin(networkCollaborationMapper.toDetailsDtoByBloodBank(shipmentRequest.getOriginBank()));
        shipmentDTO.setShipmentRequest(shipmentRequestMapper.toDtoData(shipmentRequest));
        shipmentDTO.setUnits(shipmentRequestMapper.toDetailDTO(shipmentRequest.getUnitsRequest()));

        return shipmentDTO;
    }

    @Override
    public Integer editShipment(Integer idShipment, ShipmentRequestDTO shipmentRequestDTO) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        ShipmentRequestEntity shipmentRequest = shipmentRequestRepository.findByIdWithUnits(idShipment)
                .orElseThrow(() -> new EntityNotFoundException("Shipment request not found"));

        // Actualizar datos generales
        shipmentRequest.setDetails(shipmentRequestDTO.getDetails());
        shipmentRequest.setReason(shipmentRequestDTO.getReason());
        shipmentRequest.setUpdatedAt(LocalDateTime.now());
        shipmentRequest.setUpdatedBy(userAuth);

        List<ShipmentRequestDetailEntity> currentDetails = shipmentRequest.getUnitsRequest();
        Map<Integer, ShipmentRequestDetailEntity> currentDetailsMap = currentDetails.stream()
                .collect(Collectors.toMap(ShipmentRequestDetailEntity::getId,
                        detail -> detail));

        List<ShipmentRequestDetailEntity> updatedDetails = new ArrayList<>();
        for (RequestDetailsUnitDTO dto : shipmentRequestDTO.getUnits()) {
            if (dto.getId() != null && currentDetailsMap.containsKey(dto.getId())) {
                // Editar detalle existente
                ShipmentRequestDetailEntity existingDetail = currentDetailsMap.get(dto.getId());
                existingDetail.setUnitType(dto.getUnitType());
                existingDetail.setRequestedQuantity(dto.getRequestedQuantity());
                existingDetail.setBloodType(dto.getBloodGroup());
                existingDetail.setRhFactor(dto.getRhFactor());
//                existingDetail.setUpdatedBy(userAuth);
//                existingDetail.setUpdatedAt(LocalDateTime.now());
                updatedDetails.add(existingDetail);

                currentDetailsMap.remove(dto.getId()); // Ya fue procesado
            } else {
                // Es nuevo
                ShipmentRequestDetailEntity newDetail = new ShipmentRequestDetailEntity();
                newDetail.setUnitType(dto.getUnitType());
                newDetail.setRequestedQuantity(dto.getRequestedQuantity());
                newDetail.setBloodType(dto.getBloodGroup());
                newDetail.setRhFactor(dto.getRhFactor());
                newDetail.setCreatedBy(userAuth);
                newDetail.setCreatedAt(LocalDateTime.now());
                newDetail.setShipmentRequest(shipmentRequest);
                updatedDetails.add(newDetail);
            }
        }

//        // Los que quedaron en el map son eliminados
//        for (ShipmentRequestDetailEntity toDelete : currentDetailsMap.values()) {
//            shipmentRequestDetailRepository.delete(toDelete);
//        }

        shipmentRequest.getUnitsRequest().clear(); // eliminar huérfanos
        shipmentRequest.getUnitsRequest().addAll(updatedDetails); // asignar nueva lista

        // Guardar cambios
        shipmentRequestRepository.save(shipmentRequest);
        return shipmentRequest.getId();
    }

    @Override
    public Integer sendShipment(Integer idShipment) {
        shipmentRequestRepository.updateStatusById(idShipment, ShipmentRequestStatus.SENT.getLabel());
        return idShipment;
    }

    @Override
    public ShipmentWithAssignmentDTO getShipmentWithAssignment(Integer idShipment) {
        ShipmentWithAssignmentDTO shipmentWithAssignmentDTO = new ShipmentWithAssignmentDTO();
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        ShipmentRequestEntity shipmentRequest = shipmentRequestRepository
                .findById(idShipment).orElseThrow(()->new IllegalArgumentException("Solicitud de transferencia no encontrada"));
        Integer bloodBankUser = userAuth.getBloodBank().getId();
        Integer bloodBankDestination = shipmentRequest.getDestinationBank().getId();
        Integer bloodBankOrigin = shipmentRequest.getOriginBank().getId();
        if (!Objects.equals(bloodBankUser, bloodBankDestination) && !Objects.equals(bloodBankUser, bloodBankOrigin)) {
            shipmentWithAssignmentDTO.setCanViewRequest(false);
            return shipmentWithAssignmentDTO;
        }

        shipmentWithAssignmentDTO.setCanViewRequest(true);
        shipmentWithAssignmentDTO.setBloodBankDestination(networkCollaborationMapper.toDetailsDtoByBloodBank(shipmentRequest.getDestinationBank()));
        shipmentWithAssignmentDTO.setBloodBankOrigin(networkCollaborationMapper.toDetailsDtoByBloodBank(shipmentRequest.getOriginBank()));
        shipmentWithAssignmentDTO.setShipmentRequest(shipmentRequestMapper.toDtoData(shipmentRequest));
        shipmentWithAssignmentDTO.setUnits(shipmentRequestMapper.toDetailDTO(shipmentRequest.getUnitsRequest()));
        shipmentWithAssignmentDTO.setAssignment(shipmentXUnitMapper.toDto(shipmentRequest.getUnitsAssigned()));

        return shipmentWithAssignmentDTO;
    }

}
