package com.yawarSoft.Modules.Storage.Service.Interfaces;

import com.yawarSoft.Modules.Storage.Dto.Reponse.BloodStorageDTO;

import java.util.List;

public interface BloodStorageService {

    int addBloodStorage(Integer idBloodBank, String unitType,Integer delta);

    int minusBloodStorage(Integer idBloodBank, String unitType,Integer delta);

    void initBloodStirage(Integer idBloodBank);

    BloodStorageDTO getBloodStorage();
}
