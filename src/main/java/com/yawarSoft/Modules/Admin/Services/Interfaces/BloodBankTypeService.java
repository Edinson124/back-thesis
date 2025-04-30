package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Core.Entities.BloodBankTypeEntity;

import java.util.List;

public interface BloodBankTypeService {
    List<BloodBankTypeEntity> getBloodBankTypesActive();
}
