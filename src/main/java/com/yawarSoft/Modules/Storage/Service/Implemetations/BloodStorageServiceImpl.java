package com.yawarSoft.Modules.Storage.Service.Implemetations;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.BloodStorageEntity;
import com.yawarSoft.Modules.Storage.Repositories.BloodStorageRepository;
import com.yawarSoft.Modules.Storage.Service.Interfaces.BloodStorageService;
import com.yawarSoft.Modules.Storage.Enums.UnitTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;

@Service
public class BloodStorageServiceImpl implements BloodStorageService {

    private final BloodStorageRepository bloodStorageRepository;

    public BloodStorageServiceImpl(BloodStorageRepository bloodStorageRepository) {
        this.bloodStorageRepository = bloodStorageRepository;
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
