package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Core.Entities.BloodBankTypeEntity;
import com.yawarSoft.Modules.Admin.Enums.BloodBankTypeStatus;
import com.yawarSoft.Modules.Admin.Repositories.BloodBankRepository;
import com.yawarSoft.Modules.Admin.Repositories.BloodBankTypeRepository;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodBankTypeServiceImpl implements BloodBankTypeService {

    private final BloodBankTypeRepository bloodBankTypeRepository;

    public BloodBankTypeServiceImpl(BloodBankTypeRepository bloodBankTypeRepository) {
        this.bloodBankTypeRepository = bloodBankTypeRepository;
    }

    @Override
    public List<BloodBankTypeEntity> getBloodBankTypesActive() {
        return bloodBankTypeRepository.findByStatus(BloodBankTypeStatus.ACTIVE.name());
    }
}
