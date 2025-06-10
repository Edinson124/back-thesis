package com.yawarSoft.Modules.Storage.Service.Implemetations;

import com.yawarSoft.Modules.Storage.Repositories.UnitRepository;
import com.yawarSoft.Modules.Storage.Service.Interfaces.UnitsDonationService;
import org.springframework.stereotype.Service;

@Service
public class UnitsDonationServiceImpl implements UnitsDonationService {
    private final UnitRepository unitRepository;

    public UnitsDonationServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public Integer countUnitByDonation(Long idDonation) {
        return unitRepository.countByDonation_Id(idDonation);
    }
}
