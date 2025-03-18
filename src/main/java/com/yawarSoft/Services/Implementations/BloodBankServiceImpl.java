package com.yawarSoft.Services.Implementations;

import com.yawarSoft.Dto.BloodBankDTO;
import com.yawarSoft.Dto.BloodBankProjection;
import com.yawarSoft.Mappers.RoleMapper;
import com.yawarSoft.Repositories.BloodBankRepository;
import com.yawarSoft.Repositories.RoleRepository;
import com.yawarSoft.Services.Interfaces.BloodBankService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
