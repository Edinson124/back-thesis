package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Mappers.BloodBankMapper;
import com.yawarSoft.Modules.Admin.Repositories.Projections.BloodBankProjectionSelect;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Modules.Admin.Repositories.BloodBankRepository;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodBankServiceImpl implements BloodBankService {

    private final BloodBankRepository bloodBankRepository;
    private final BloodBankMapper bloodBankMapper;

    public BloodBankServiceImpl(BloodBankRepository bloodBankRepository, BloodBankMapper bloodBankMapper) {
        this.bloodBankRepository = bloodBankRepository;
        this.bloodBankMapper = bloodBankMapper;
    }

    @Override
    public List<BloodBankSelectOptionDTO> getBloodBankSelector() {
        List<BloodBankProjectionSelect> bloodBankSelectProjection = bloodBankRepository.getBloodBankSelect();
        return bloodBankMapper.toSelectDtoListFromProjectionList(bloodBankSelectProjection);
    }

    @Override
    public Optional<BloodBankEntity> getBloodBankEntityById(Integer id) {
        return bloodBankRepository.findById(id);
    }
}
