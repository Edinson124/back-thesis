package com.yawarSoft.Modules.Network.Services.Implementations;

import com.yawarSoft.Core.Entities.*;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Network.Dto.ShipmentXUnitDTO;
import com.yawarSoft.Modules.Network.Mappers.ShipmentXUnitMapper;
import com.yawarSoft.Modules.Network.Repositories.ShipmentAssingmentRepository;
import com.yawarSoft.Modules.Network.Repositories.ShipmentRequestRepository;
import com.yawarSoft.Modules.Network.Services.Interfaces.ShipmentAssignmentService;
import com.yawarSoft.Modules.Storage.Enums.UnitStatus;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import com.yawarSoft.Modules.Transfusion.Enums.TransfusionAssingmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShipmentAssignmentServiceImpl implements ShipmentAssignmentService {

    private final ShipmentAssingmentRepository shipmentAssingmentRepository;
    private final ShipmentRequestRepository shipmentRequestRepository;
    private final UnitService unitService;
    private final ShipmentXUnitMapper shipmentXUnitMapper;
    private final AuthenticatedUserService authenticatedUserService;

    public ShipmentAssignmentServiceImpl(ShipmentAssingmentRepository shipmentAssingmentRepository, ShipmentRequestRepository shipmentRequestRepository, UnitService unitService, ShipmentXUnitMapper shipmentXUnitMapper, AuthenticatedUserService authenticatedUserService) {
        this.shipmentAssingmentRepository = shipmentAssingmentRepository;
        this.shipmentRequestRepository = shipmentRequestRepository;
        this.unitService = unitService;
        this.shipmentXUnitMapper = shipmentXUnitMapper;
        this.authenticatedUserService = authenticatedUserService;
    }


    @Override
    public ShipmentXUnitDTO saveShipmentAssignment(Integer idShipmentRequest, Long idUnit) {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        ShipmentRequestEntity shipmentRequest = shipmentRequestRepository.findById(idShipmentRequest)
                .orElseThrow(() -> new IllegalArgumentException("Transfusión no encontrada con ID: " + idShipmentRequest));
        UnitEntity unitEntity = UnitEntity.builder().id(idUnit).build();

        ShipmentXUnitEntity shipmentXUnit = new ShipmentXUnitEntity();
        shipmentXUnit.setShipmentRequest(shipmentRequest);
        shipmentXUnit.setBloodUnit(unitEntity);
        shipmentXUnit.setStatus("ACTIVE");
        shipmentXUnit.setCreatedBy(userAuth);
        shipmentXUnit.setCreatedAt(LocalDateTime.now());

        ShipmentXUnitEntity shipmentXUnitSaved = shipmentAssingmentRepository.save(shipmentXUnit);
        ShipmentXUnitEntity shipmentXUnitFinal = shipmentAssingmentRepository
                .findByIdWithUnit(shipmentXUnitSaved.getId())
                .orElseThrow(() -> new RuntimeException("No se encontró la asignación"));
        unitService.updateStatusUnit(idUnit, UnitStatus.RESERVED.getLabel());
        return shipmentXUnitMapper.toDto(shipmentXUnitFinal);
    }

    @Override
    public Integer deleteShipmentAssignment(Integer idShipmentAssignment) {
        ShipmentXUnitEntity shipmentXUnit = shipmentAssingmentRepository.findById(idShipmentAssignment)
                .orElseThrow(() -> new IllegalArgumentException("Transfusión no encontrada con ID: " + idShipmentAssignment));


        Long idUnit = shipmentXUnit.getBloodUnit().getId();
        shipmentAssingmentRepository.deleteById(idShipmentAssignment);

        unitService.updateStatusUnit(idUnit, UnitStatus.SUITABLE.getLabel());
        return shipmentXUnit.getId();
    }
}
