package com.yawarSoft.Modules.Storage.Service.Implemetations;

import com.yawarSoft.Core.Entities.*;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Admin.Dto.GlobalVariableDTO;
import com.yawarSoft.Modules.Admin.Services.Interfaces.GlobalVariableService;
import com.yawarSoft.Modules.Donation.Services.Interfaces.DonationService;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitExtractionDTO;
import com.yawarSoft.Modules.Storage.Dto.Reponse.UnitListDTO;
import com.yawarSoft.Modules.Storage.Dto.UnitDTO;
import com.yawarSoft.Modules.Storage.Enums.UnitStatus;
import com.yawarSoft.Modules.Storage.Enums.UnitTypes;
import com.yawarSoft.Modules.Storage.Mappers.UnitMapper;
import com.yawarSoft.Modules.Storage.Repositories.UnitRepository;
import com.yawarSoft.Modules.Storage.Repositories.UnitStorageRepository;
import com.yawarSoft.Modules.Storage.Repositories.UnitTransformationRepository;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitService;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionAssignmentService;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionBloodCompatible;
import jakarta.persistence.criteria.CriteriaBuilder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitTransformationRepository unitTransformationRepository;
    private final UnitStorageRepository unitStorageRepository;
    private final UnitMapper unitMapper;
    private final GlobalVariableService globalVariableService;
    private final DonationService donationService;
    private final AuthenticatedUserService authenticatedUserService;
    private final TransfusionBloodCompatible transfusionBloodCompatible;

    public UnitServiceImpl(UnitRepository unitRepository, UnitTransformationRepository unitTransformationRepository, GlobalVariableService globalVariableService, UnitStorageRepository unitStorageRepository, UnitMapper unitMapper, DonationService donationService, AuthenticatedUserService authenticatedUserService, TransfusionBloodCompatible transfusionBloodCompatible) {
        this.unitRepository = unitRepository;
        this.unitTransformationRepository = unitTransformationRepository;
        this.globalVariableService = globalVariableService;
        this.unitStorageRepository = unitStorageRepository;
        this.unitMapper = unitMapper;
        this.donationService = donationService;
        this.authenticatedUserService = authenticatedUserService;
        this.transfusionBloodCompatible = transfusionBloodCompatible;
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

            // Filtrar por unidades en cuarentena (status = 'Disponible')
            predicates.add(cb.equal(root.get("bloodBank").get("id"), bloodBankId));
            predicates.add(cb.equal(root.get("status"), UnitStatus.SUITABLE.getLabel()));
//            predicates.add(
//                    cb.or(
//                            cb.equal(root.get("status"), UnitStatus.SUITABLE.getLabel()),
//                            cb.equal(root.get("status"), UnitStatus.FRACTIONATED.getLabel())
//                    )
//            );
            if (bloodType != null && !bloodType.isBlank()) {
                predicates.add(cb.equal(root.get("bloodType"), bloodType));
            }

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
            unit.setStatus(UnitStatus.REACTIVE.getLabel());
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


    @Override
    public Page<UnitListDTO> getUnitsStock(int page, int size, LocalDate startEntryDate, LocalDate
            endEntryDate, LocalDate startExpirationDate, LocalDate endExpirationDate, String bloodType,
                                           String type, String status, Long idTransfusion) {


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

            // Estado
            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), status));
            } else if (idTransfusion != null) {
                // No status but idTransfusion → SUITABLE
                predicates.add(cb.equal(root.get("status"), UnitStatus.SUITABLE.getLabel()));
            } else {
                predicates.add(
                        cb.or(
                                cb.equal(root.get("status"), UnitStatus.SUITABLE.getLabel()),
                                cb.equal(root.get("status"), UnitStatus.RESERVED.getLabel())
                        )
                );
            }

            if (type != null && !type.isBlank()) {
                predicates.add(cb.equal(root.get("unitType"), type));
            }
            // Blood type: por filtro directo o por compatibilidad
            List<String> bloodTypesToUse = new ArrayList<>();
            if (bloodType != null && !bloodType.isBlank()) {
                bloodTypesToUse.add(bloodType);
            } else if (idTransfusion != null) {
                bloodTypesToUse = transfusionBloodCompatible.getBloodTypeCompatibleString(idTransfusion);
            }

            if (!bloodTypesToUse.isEmpty()) {
                if (bloodTypesToUse.size() == 1) {
                    predicates.add(cb.equal(root.get("bloodType"), bloodTypesToUse.get(0)));
                } else {
                    CriteriaBuilder.In<String> inClause = cb.in(root.get("bloodType"));
                    for (String bt : bloodTypesToUse) {
                        inClause.value(bt);
                    }
                    predicates.add(inClause);
                }
            }

            predicates.add(cb.equal(root.get("bloodBank").get("id"), bloodBankId));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<UnitEntity> unitsPage = unitRepository.findAll(spec, pageable);

        // Aquí puedes mapear UnitEntity → UnitListDTO
        return unitsPage.map(unitMapper::toListDTO);

    }

    @Override
    public List<UnitExtractionDTO> getUnitsByDonation(Long idDonation) {
        List<UnitEntity> units = unitRepository.findByDonationId(idDonation);
        return units.stream()
                .map(unitMapper::toExtractionDTO)
                .toList();
    }

    @Transactional
    @Override
    public UnitExtractionDTO saveUnitDonation(Long idDonation, UnitExtractionDTO unit) {
        Map<String, String> values = donationService.getBloodTypeAndSerology(idDonation);
        String bloodType = values.get("bloodType");
        String serologyResult = values.get("serologyResult");
        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();

        String code = UnitTypes.getLifeTimeByLabel(unit.getType());

        GlobalVariableDTO globalVariableDTO = globalVariableService.getByCode(code);
        Integer days = Integer.parseInt(globalVariableDTO.getValue());

        LocalDate date = LocalDate.now();
        LocalDate dateExpiration = date.plusDays(days);

        DonationEntity donationEntity = new DonationEntity();
        donationEntity.setId(idDonation);

        UnitEntity unitEntity = unitMapper.toEntityByExtractionDTO(unit);
        unitEntity.setExpirationDate(dateExpiration);
        unitEntity.setBloodType(bloodType);
        unitEntity.setDonation(donationEntity);
        unitEntity.setCreatedAt(LocalDateTime.now());
        unitEntity.setCreatedBy(userAuthenticated);
        unitEntity.setBloodBank(userAuthenticated.getBloodBank());
        unitEntity.setEntryDate(date);
        unitEntity.setStatus(UnitStatus.QUARANTINED.getLabel());
        unitEntity.setSerologyResult(serologyResult);

        UnitEntity result = unitRepository.save(unitEntity);
        unit.setId(result.getId());

        UnitStorageEntity unitStorage = new UnitStorageEntity();
        unitStorage.setUnit(result);
        unitStorage.setBloodBank(userAuthenticated.getBloodBank());
        unitStorage.setCreatedAt(LocalDateTime.now());
        unitStorage.setEntryDate(LocalDateTime.now());
        unitStorage.setCreatedBy(userAuthenticated);
        unitStorageRepository.save(unitStorage);
        return unit;
    }

    @Override
    public UnitExtractionDTO editUnit(Long idUnit, UnitExtractionDTO unit) {
        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        UnitEntity unitEntity = unitRepository.findById(idUnit)
                .orElseThrow( () -> new IllegalArgumentException("No se encontro la unidad con el id: " + idUnit));
        unitEntity.setUnitType(unit.getType());
        unitEntity.setAnticoagulant(unit.getAnticoagulant());
        unitEntity.setBagType(unit.getBag());
        unitEntity.setVolume(unit.getVolume());
        unitEntity.setUpdatedAt(LocalDateTime.now());
        unitEntity.setUpdatedBy(userAuthenticated);
        unitRepository.save(unitEntity);
        return unit;
    }

    @Override
    @Transactional
    public UnitExtractionDTO saveUnitTransformation(Long idUnit, UnitExtractionDTO unit) {
        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        UnitEntity unitEntityOrigin = unitRepository.findById(idUnit)
                .orElseThrow( () -> new IllegalArgumentException("No se encontro el id: " + idUnit));

        String code = UnitTypes.getLifeTimeByLabel(unit.getType());
        GlobalVariableDTO globalVariableDTO = globalVariableService.getByCode(code);
        Integer days = Integer.parseInt(globalVariableDTO.getValue());
        LocalDate date = unitEntityOrigin.getEntryDate();
        LocalDate dateExpiration = date.plusDays(days);

        UnitEntity unitEntityGenerated = unitMapper.toEntityByExtractionDTO(unit);
        unitEntityGenerated.setExpirationDate(dateExpiration);
        unitEntityGenerated.setBloodType(unitEntityOrigin.getBloodType());
        unitEntityGenerated.setDonation(unitEntityOrigin.getDonation());
        unitEntityGenerated.setCreatedAt(LocalDateTime.now());
        unitEntityGenerated.setCreatedBy(userAuthenticated);
        unitEntityGenerated.setBloodBank(userAuthenticated.getBloodBank());
        unitEntityGenerated.setEntryDate(date);
        unitEntityGenerated.setStatus(UnitStatus.SUITABLE.getLabel());
        unitEntityGenerated.setSerologyResult(unitEntityOrigin.getSerologyResult());

        UnitEntity result = unitRepository.save(unitEntityGenerated);
        unit.setId(result.getId());

        UnitTransformationEntity unitTransformation = new UnitTransformationEntity();
        unitTransformation.setOriginUnit(unitEntityOrigin);
        unitTransformation.setGeneratedUnit(unitEntityGenerated);
        unitTransformation.setTransformationDate(LocalDateTime.now());
        unitTransformation.setCreatedBy(userAuthenticated);
        unitTransformation.setCreatedAt(LocalDateTime.now());
        unitTransformationRepository.save(unitTransformation);

        unitRepository.updateStatusById(unitEntityOrigin.getId(), UnitStatus.FRACTIONATED.getLabel());
        return unit;
    }

    @Override
    public Long unitSuitable(Long idUnit) {
        String status = UnitStatus.SUITABLE.getLabel();
        unitRepository.updateStatusById(idUnit, status);
        return idUnit;
    }

    @Override
    public List<UnitExtractionDTO> getUnitsTransformationByUnit(Long idUnit) {
        List<UnitEntity> generatedUnits = unitTransformationRepository.findGeneratedUnitsByOriginUnitId(idUnit);
        return generatedUnits.stream()
                .map(unitMapper::toExtractionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Integer updateBloodTypeIfHematologicalTestAfter(Long idDonation, String bloodType) {
        return unitRepository.updateBloodTypeByDonationId(idDonation, bloodType);
    }

    @Override
    public void updateStatusUnit(Long idUnit, String status) {
        unitRepository.updateStatusById(idUnit, status);
    }

}
