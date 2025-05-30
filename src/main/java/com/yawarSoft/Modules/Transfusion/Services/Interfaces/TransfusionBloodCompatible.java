package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Modules.Transfusion.Dto.BloodTypeOption;

import java.util.List;

public interface TransfusionBloodCompatible {
    List<String> getBloodTypeCompatibleString(Long idTransfusion);
    List<BloodTypeOption> getBloodTypeCompatible(Long idTransfusion);

}
