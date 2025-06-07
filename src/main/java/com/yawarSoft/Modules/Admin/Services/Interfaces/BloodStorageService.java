package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Modules.Admin.Dto.GlobalVariableDTO;

public interface BloodStorageService {

    int addBloodStorage(Integer idBloodBank, String unitType);

    int minusBloodStorage(Integer idBloodBank, String unitType);
}
