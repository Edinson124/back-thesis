package com.yawarSoft.Services.Implementations;

import com.yawarSoft.Dto.BloodBankProjection;
import com.yawarSoft.Entities.BloodBankEntity;
import com.yawarSoft.Mappers.RoleMapper;
import com.yawarSoft.Repositories.BloodBankRepository;
import com.yawarSoft.Services.Interfaces.BloodBankService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodBankServiceImpl implements BloodBankService {

    private final BloodBankRepository bloodBankRepository;

    public BloodBankServiceImpl(BloodBankRepository bloodBankRepository, RoleMapper roleMapper) {
        this.bloodBankRepository = bloodBankRepository;
    }
    @Override
    public List<BloodBankProjection> getBloodBankSelector() {
        return bloodBankRepository.getBloodBankSelect();
    }

    @Override
    public Optional<BloodBankEntity> getBloodBankEntityById(Integer id) {
        return bloodBankRepository.findById(id);
    }
}
