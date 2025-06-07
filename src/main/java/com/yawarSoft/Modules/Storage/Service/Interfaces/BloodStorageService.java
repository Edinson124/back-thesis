package com.yawarSoft.Modules.Storage.Service.Interfaces;

public interface BloodStorageService {

    int addBloodStorage(Integer idBloodBank, String unitType,Integer delta);

    int minusBloodStorage(Integer idBloodBank, String unitType,Integer delta);

    void initBloodStirage(Integer idBloodBank);
}
