package com.yawarSoft.Modules.Storage.Service.Implemetations;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.BloodStorageEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Storage.Dto.Reponse.BloodStorageDTO;
import com.yawarSoft.Modules.Storage.Repositories.BloodStorageRepository;
import com.yawarSoft.Modules.Storage.Service.Interfaces.BloodStorageService;
import com.yawarSoft.Modules.Storage.Enums.UnitTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BloodStorageServiceImpl implements BloodStorageService {

    private final BloodStorageRepository bloodStorageRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public BloodStorageServiceImpl(BloodStorageRepository bloodStorageRepository, AuthenticatedUserService authenticatedUserService) {
        this.bloodStorageRepository = bloodStorageRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public int addBloodStorage(Integer idBloodBank, String unitType, Integer delta) {
        BloodStorageEntity storage = bloodStorageRepository.findById(idBloodBank)
                .orElseThrow(() -> new IllegalStateException("No se encontró el registro de almacenamiento para el banco de sangre."));

        updateBloodStorage(storage, unitType, delta);
        bloodStorageRepository.save(storage);
        return idBloodBank;
    }

    @Override
    public int minusBloodStorage(Integer idBloodBank, String unitType,Integer delta) {
        BloodStorageEntity storage = bloodStorageRepository.findById(idBloodBank)
                .orElseThrow(() -> new IllegalStateException("No se encontró el registro de almacenamiento para el banco de sangre."));

        updateBloodStorage(storage, unitType, -delta);
        bloodStorageRepository.save(storage);
        return idBloodBank;
    }

    @Transactional
    @Override
    public void initBloodStirage(Integer idBloodBank) {
        bloodStorageRepository.insertInitialStorage(idBloodBank);
    }

    @Override
    public BloodStorageDTO getBloodStorage() {
        UserEntity userAuthenticated = authenticatedUserService.getCurrentUser();
        Integer bloodBankId = userAuthenticated.getBloodBank().getId();
        BloodStorageEntity bloodStorageEntity = bloodStorageRepository.findById(bloodBankId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró almacenamiento para el banco de sangre con ID: " + bloodBankId));

        return BloodStorageDTO.builder()
                .totalBlood(bloodStorageEntity.getTotalBlood())
                .erythrocyteConcentrate(bloodStorageEntity.getErythrocyteConcentrate())
                .freshFrozenPlasma(bloodStorageEntity.getFreshFrozenPlasma())
                .cryoprecipitate(bloodStorageEntity.getCryoprecipitate())
                .platelet(bloodStorageEntity.getPlatelet())
                .plateletApheresis(bloodStorageEntity.getPlateletApheresis())
                .redBloodCellsApheresis(bloodStorageEntity.getRedBloodCellsApheresis())
                .plasmaApheresis(bloodStorageEntity.getPlasmaApheresis())
                .build();
    }


    private void updateBloodStorage(BloodStorageEntity storage, String unitTypeLabel, int delta) {
        UnitTypes unitType = Arrays.stream(UnitTypes.values())
                .filter(t -> t.getLabel().equalsIgnoreCase(unitTypeLabel))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de unidad desconocido: " + unitTypeLabel));

        switch (unitType) {
            case SANGRE_TOTAL -> storage.setTotalBlood(storage.getTotalBlood() + delta);
            case CONCENTRADO_ERITROCITOS -> storage.setErythrocyteConcentrate(storage.getErythrocyteConcentrate() + delta);
            case PLASMA_FRESCO_CONGELADO -> storage.setFreshFrozenPlasma(storage.getFreshFrozenPlasma() + delta);
            case CRIOPRECIPITADOS -> storage.setCryoprecipitate(storage.getCryoprecipitate() + delta);
            case PLAQUETAS -> storage.setPlatelet(storage.getPlatelet() + delta);
            case AFERESIS_PLAQUETAS -> storage.setPlateletApheresis(storage.getPlateletApheresis() + delta);
            case AFERESIS_GLOBULOS_ROJOS -> storage.setRedBloodCellsApheresis(storage.getRedBloodCellsApheresis() + delta);
            case AFERESIS_PLASMA -> storage.setPlasmaApheresis(storage.getPlasmaApheresis() + delta);
            default -> throw new IllegalStateException("Unidad no manejada: " + unitType.name());
        }
    }

}
