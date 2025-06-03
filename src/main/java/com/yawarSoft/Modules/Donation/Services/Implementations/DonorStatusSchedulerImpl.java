package com.yawarSoft.Modules.Donation.Services.Implementations;

import com.yawarSoft.Modules.Donation.Enums.DonorStatus;
import com.yawarSoft.Modules.Donation.Repositories.DonorRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DonorStatusSchedulerImpl {
    private final DonorRepository donorRepository;

    public DonorStatusSchedulerImpl(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Scheduled(cron = "0 1 0 * * *")
    public void updateTemporarilyDeferredDonors() {
        LocalDate today = LocalDate.now();

        Integer result = donorRepository.updateDeferredDonorsToEligible(DonorStatus.ELIGIBLE.getLabel()
                ,DonorStatus.TEMPORARILY_DEFERRED.getLabel(), today
        );
    }
}
