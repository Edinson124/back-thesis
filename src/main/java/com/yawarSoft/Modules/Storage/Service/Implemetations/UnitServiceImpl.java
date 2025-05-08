package com.yawarSoft.Modules.Storage.Service.Implemetations;

import com.yawarSoft.Core.Entities.UnitEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Dto.UnitDTO;
import com.yawarSoft.Modules.Storage.Enums.UnitStatus;
import com.yawarSoft.Modules.Storage.Enums.UnitTypes;
import com.yawarSoft.Modules.Storage.Mappers.UnitMapper;
import com.yawarSoft.Modules.Storage.Repositories.UnitRepository;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
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
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    private final AuthenticatedUserService authenticatedUserService;

    public UnitServiceImpl(UnitRepository unitRepository, UnitMapper unitMapper, AuthenticatedUserService authenticatedUserService) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
        this.authenticatedUserService = authenticatedUserService;
    }


    @Override
    public Page<UnitListDTO> getUnitsQuarantined(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate,
                                      LocalDate startExpirationDate, LocalDate endExpirationDate, String bloodType,
                                      String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        Integer bloodBankId = userAuthenticated.getBloodBank().getId();

        Specification<UnitEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Fecha de ingreso
            if (startEntryDate != null && endEntryDate != null) {
                predicates.add(cb.between(root.get("entryDate"), startEntryDate, endEntryDate));
            } else if (startEntryDate != null) {
                predicates.add(cb.equal(root.get("entryDate"), startEntryDate));
            } else if (endEntryDate != null) {
                predicates.add(cb.equal(root.get("entryDate"), endEntryDate));
            }

            // Fecha de vencimiento
            if (startExpirationDate != null && endExpirationDate != null) {
                predicates.add(cb.between(root.get("expirationDate"), startExpirationDate, endExpirationDate));
            } else if (startExpirationDate != null) {
                predicates.add(cb.equal(root.get("expirationDate"), startExpirationDate));
            } else if (endExpirationDate != null) {
                predicates.add(cb.equal(root.get("expirationDate"), endExpirationDate));
            }

            if (bloodType != null && !bloodType.isBlank()) {
                predicates.add(cb.equal(root.get("bloodType"), bloodType));
            }

            if (type != null && !type.isBlank()) {
                predicates.add(cb.equal(root.get("unitType"), type));
            }

            // Filtrar por unidades en cuarentena (status = 'En cuarentena')
            predicates.add(cb.equal(root.get("bloodBank").get("id"), bloodBankId));
            predicates.add(cb.equal(root.get("status"), UnitStatus.QUARANTINED.getLabel()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<UnitEntity> unitsPage = unitRepository.findAll(spec, pageable);

        // Aquí puedes mapear UnitEntity → UnitListDTO
        return unitsPage.map(unitMapper::toListDTO);

    }

    @Override
    public Page<UnitListDTO> getUnitsTransformation(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, LocalDate startExpirationDate, LocalDate endExpirationDate, String bloodType, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        Integer bloodBankId = userAuthenticated.getBloodBank().getId();

        Specification<UnitEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Fecha de vencimiento
            if (startExpirationDate != null && endExpirationDate != null) {
                predicates.add(cb.between(root.get("expirationDate"), startExpirationDate, endExpirationDate));
            } else if (startExpirationDate != null) {
                predicates.add(cb.equal(root.get("expirationDate"), startExpirationDate));
            } else if (endExpirationDate != null) {
                predicates.add(cb.equal(root.get("expirationDate"), endExpirationDate));
            }

            // Fecha de ingreso
            if (startEntryDate != null && endEntryDate != null) {
                predicates.add(cb.between(root.get("entryDate"), startEntryDate, endEntryDate));
            } else if (startEntryDate != null) {
                predicates.add(cb.equal(root.get("entryDate"), startEntryDate));
            } else if (endEntryDate != null) {
                predicates.add(cb.equal(root.get("entryDate"), endEntryDate));
            }

            // Filtro por tipo
            if (type != null && !type.isBlank()) {
                predicates.add(cb.equal(root.get("unitType"), type));
            } else {
                predicates.add(
                        cb.or(
                                cb.equal(root.get("unitType"), UnitTypes.SANGRE_TOTAL.getLabel()),
                                cb.equal(root.get("unitType"), UnitTypes.PLASMA_FRESCO_CONGELADO.getLabel())
                        )
                );
            }

            if (type != null && !type.isBlank()) {
                predicates.add(cb.equal(root.get("unitType"), type));
            }

            // Filtrar por unidades en cuarentena (status = 'Disponible')
            predicates.add(cb.equal(root.get("bloodBank").get("id"), bloodBankId));
            predicates.add(cb.equal(root.get("status"), UnitStatus.DISPONIBLE.getLabel()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<UnitEntity> unitsPage = unitRepository.findAll(spec, pageable);

        // Aquí puedes mapear UnitEntity → UnitListDTO
        return unitsPage.map(unitMapper::toListDTO);

    }

    @Override
    public boolean updateUnitsReactiveTestSerologyById(Long donationId, String result) {
        List<UnitEntity> units = unitRepository.findByDonationId(donationId);
        if (units.isEmpty()) {
            return false; // No hay unidades que actualizar
        }
        for (UnitEntity unit : units) {
            unit.setStatus(UnitStatus.REACTIVO.getLabel());
        }
        unitRepository.saveAll(units); // Guardar todas las unidades modificadas
        return true;
    }

    @Override
    public boolean updateUnitsNoReactiveTestSerologyById(Long donationId, String result) {
        List<UnitEntity> units = unitRepository.findByDonationId(donationId);
        if (units.isEmpty()) {
            return false; // No hay unidades que actualizar
        }
        for (UnitEntity unit : units) {
            unit.setSerologyResult(result);
        }
        unitRepository.saveAll(units); // Guardar todas las unidades modificadas
        return true;
    }

    @Override
    public UnitDTO getUnitById(Long id) {
        UnitEntity unit = unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unidad no encontrada con id: " + id));

        return unitMapper.toDTO(unit);
    }

}
