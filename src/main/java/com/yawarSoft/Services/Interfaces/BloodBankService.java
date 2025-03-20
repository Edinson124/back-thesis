package com.yawarSoft.Services.Interfaces;

import com.yawarSoft.Dto.BloodBankProjection;
import com.yawarSoft.Entities.BloodBankEntity;

import java.util.List;
import java.util.Optional;

public interface BloodBankService {
    List<BloodBankProjection> getBloodBankSelector();

    Optional<BloodBankEntity> getBloodBankEntityById(Integer id);
}
