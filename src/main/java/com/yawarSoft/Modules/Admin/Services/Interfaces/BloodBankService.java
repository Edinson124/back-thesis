package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import com.yawarSoft.Core.Entities.BloodBankEntity;

import java.util.List;
import java.util.Optional;

public interface BloodBankService {
    List<BloodBankSelectOptionDTO> getBloodBankSelector();

    Optional<BloodBankEntity> getBloodBankEntityById(Integer id);
}
