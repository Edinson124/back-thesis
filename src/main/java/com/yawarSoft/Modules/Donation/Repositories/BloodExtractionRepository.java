package com.yawarSoft.Modules.Donation.Repositories;

import com.yawarSoft.Core.Entities.BloodExtractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodExtractionRepository extends JpaRepository<BloodExtractionEntity, Long> {
}
